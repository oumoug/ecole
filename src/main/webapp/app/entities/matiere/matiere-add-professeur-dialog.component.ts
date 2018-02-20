import { Component, OnInit,OnDestroy  } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';
import { MatierePopupService } from './matiere-popup.service';
import { MatiereService} from './matiere.service';
import { Professeur, ProfesseurService } from '../professeur';
import { Matiere } from './matiere.model';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
@Component({
  selector: 'jhi-matiere-add-professeur-dialog',
  templateUrl: './matiere-add-professeur-dialog.component.html',
  styles: []
})
export class MatiereAddProfesseurDialogComponent implements OnInit {
  matiere: Matiere;
  professeurs: Professeur[];
  constructor(public activeModal: NgbActiveModal,
    private jhiAlertService: JhiAlertService,
    private matiereService: MatiereService,
    private professeurService: ProfesseurService,
    private eventManager: JhiEventManager,
    private route: ActivatedRoute) { }

  ngOnInit() {
    
    this.professeurService.query()
            .subscribe((res: HttpResponse<Professeur[]>) => { this.professeurs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
  }
  private onError(error: any) {
    this.jhiAlertService.error(error.message, null, null);
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
  
  clear() {
    this.activeModal.dismiss('cancel');
  }

    save() {
    
        if (this.matiere.id !== undefined) {
            this.subscribeToSaveResponse(
                this.matiereService.update(this.matiere));
        } 
    }
    private subscribeToSaveResponse(result: Observable<HttpResponse<Matiere>>) {
        result.subscribe((res: HttpResponse<Matiere>) =>
        this.onSaveSuccess(res.body));
    }
    private onSaveSuccess(result: Matiere) {
        this.eventManager.broadcast({ name: 'matiereListModification', content: 'OK'});
        this.activeModal.dismiss(result);
    }

}
@Component({
  selector: 'jhi-matiere-add-Professeur-popup',
  template: ''
})
export class MatiereAddProfesseurPopupComponent implements OnInit, OnDestroy {

  routeSub: any;

  constructor(
      private route: ActivatedRoute,
      private matierePopupService: MatierePopupService
  ) {}

  ngOnInit() {
      this.routeSub = this.route.params.subscribe((params) => {
          if ( params['id'] ) {
              this.matierePopupService
                  .open(MatiereAddProfesseurDialogComponent as Component, params['id']);
          } 
      });
  }

  ngOnDestroy() {
      this.routeSub.unsubscribe();
  }
}
