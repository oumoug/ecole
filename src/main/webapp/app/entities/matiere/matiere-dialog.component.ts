import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Matiere } from './matiere.model';
import { MatierePopupService } from './matiere-popup.service';
import { MatiereService } from './matiere.service';
import { Professeur, ProfesseurService } from '../professeur';
import { Classe, ClasseService } from '../classe';

@Component({
    selector: 'jhi-matiere-dialog',
    templateUrl: './matiere-dialog.component.html'
})
export class MatiereDialogComponent implements OnInit {

    matiere: Matiere;
    isSaving: boolean;

    professeurs: Professeur[];

    classes: Classe[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private matiereService: MatiereService,
        private professeurService: ProfesseurService,
        private classeService: ClasseService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.professeurService.query()
            .subscribe((res: HttpResponse<Professeur[]>) => { this.professeurs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.classeService.query()
            .subscribe((res: HttpResponse<Classe[]>) => { this.classes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.matiere.id !== undefined) {
            this.subscribeToSaveResponse(
                this.matiereService.update(this.matiere));
        } else {
            this.subscribeToSaveResponse(
                this.matiereService.create(this.matiere));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Matiere>>) {
        result.subscribe((res: HttpResponse<Matiere>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Matiere) {
        this.eventManager.broadcast({ name: 'matiereListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProfesseurById(index: number, item: Professeur) {
        return item.id;
    }

    trackClasseById(index: number, item: Classe) {
        return item.id;
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
    selector: 'jhi-matiere-popup',
    template: ''
})
export class MatierePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private matierePopupService: MatierePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.matierePopupService
                    .open(MatiereDialogComponent as Component, params['id']);
            } else {
                this.matierePopupService
                    .open(MatiereDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
