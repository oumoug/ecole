import { Component, OnInit,OnDestroy} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProfesseurService } from './professeur.service';
import { ProfesseurPopupService } from './professeur-popup.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Classe, ClasseService } from '../classe';
import { Professeur } from './professeur.model';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'jhi-professeur-add-classe-dialog',
  templateUrl: './professeur-add-classe-dialog.component.html',
  styles: []
})
export class ProfesseurAddClasseDialogComponent implements OnInit {
  professeur: Professeur;
  classes: Classe[];

  constructor(
    public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private professeurService: ProfesseurService,
        private classeService: ClasseService,
        private eventManager: JhiEventManager
  ) { }

  ngOnInit() {
    this.classeService.query()
    .subscribe((res: HttpResponse<Classe[]>) => { this.classes = res.body; },(res: HttpErrorResponse) => this.onError(res.message));
  }
  private onError(error: any) {
    this.jhiAlertService.error(error.message, null, null);
  }
  clear() {
    this.activeModal.dismiss('cancel');
}

  save() {
      
      if (this.professeur.id !== undefined) {
          this.subscribeToSaveResponse(
              this.professeurService.update(this.professeur));
      } 
     
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<Professeur>>) {
      result.subscribe((res: HttpResponse<Professeur>) =>
          this.onSaveSuccess(res.body));
  }

  private onSaveSuccess(result: Professeur) {
      this.eventManager.broadcast({ name: 'professeurListModification', content: 'OK'});
      this.activeModal.dismiss(result);
  }
  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
        for (let i = 0; i < selectedVals.length; i++) {
            if (option.id === selectedVals[i].id) {
                return selectedVals[i];
            }
        }
    }
    return option;
  }

}

@Component({
  selector: 'jhi-professeur-add-classe-popup',
  template: ''
})
export class ProfesseurAddClassePopupComponent implements OnInit, OnDestroy {

  routeSub: any;

  constructor(
      private route: ActivatedRoute,
      private professeurPopupService: ProfesseurPopupService
  ) {}

  ngOnInit() {
      this.routeSub = this.route.params.subscribe((params) => {
          if ( params['id'] ) {
              this.professeurPopupService
                  .open(ProfesseurAddClasseDialogComponent as Component, params['id']);
          }
      });

  }
  ngOnDestroy() {
      this.routeSub.unsubscribe();
  }
}
