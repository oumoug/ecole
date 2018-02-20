import { Component, OnInit, OnDestroy  } from '@angular/core';
import { Classe } from './classe.model';
import { ClassePopupService } from './classe-popup.service';
import { ClasseService } from './classe.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager,JhiAlertService } from 'ng-jhipster';
import { Matiere, MatiereService } from '../matiere';
import { Observable } from 'rxjs/Observable';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'jhi-classe-addmatiere-dialog',
  templateUrl: './classe-addmatiere-dialog.component.html',
  styles: []
})
export class ClasseAddmatiereDialogComponent implements OnInit {
  classe: Classe;
  matieres: Matiere[];
  routeSub: any;
  constructor( private classeService: ClasseService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager,
    private matiereService:MatiereService,
    private route: ActivatedRoute,
    private classePopupService: ClassePopupService, 
    private jhiAlertService: JhiAlertService
  ) {}

  ngOnInit() {
    this.matiereService.query()
            .subscribe((res: HttpResponse<Matiere[]>) => { this.matieres = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
  }
  clear() {
    this.activeModal.dismiss('cancel');
}
  save() {
    if (this.classe.id !== undefined) {
        this.subscribeToSaveResponse(
            this.classeService.update(this.classe));
    }
}
private subscribeToSaveResponse(result: Observable<HttpResponse<Classe>>) {
  result.subscribe((res: HttpResponse<Classe>) =>
      this.onSaveSuccess(res.body));
}

private onSaveSuccess(result: Classe) {
  this.eventManager.broadcast({ name: 'classeListModification', content: 'OK'});
  this.activeModal.dismiss(result);
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



}
@Component({
  selector: 'jhi-classe-addMatiere-popup',
  template: ''
})
export class ClasseAddmatierePopupComponent implements OnInit, OnDestroy {

  routeSub: any;

  constructor(
      private route: ActivatedRoute,
      private classePopupService: ClassePopupService
  ) {}

  ngOnInit() {
    this.routeSub = this.route.params.subscribe((params) => {
      if(params['id'] ){
          this.classePopupService
              .open(ClasseAddmatiereDialogComponent as Component, params['id']);
      }
    });
  }
  ngOnDestroy() {
      this.routeSub.unsubscribe();
  }
}
