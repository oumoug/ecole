import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Professeur } from './professeur.model';
import { ProfesseurPopupService } from './professeur-popup.service';
import { ProfesseurService } from './professeur.service';
import { Adresse, AdresseService } from '../adresse';
import { Classe, ClasseService } from '../classe';
import { Matiere, MatiereService } from '../matiere';

@Component({
    selector: 'jhi-professeur-dialog',
    templateUrl: './professeur-dialog.component.html'
})
export class ProfesseurDialogComponent implements OnInit {

    professeur: Professeur;
    isSaving: boolean;

    adresseprofesseurs: Adresse[];

    classes: Classe[];

    matieres: Matiere[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private professeurService: ProfesseurService,
        private adresseService: AdresseService,
        private classeService: ClasseService,
        private matiereService: MatiereService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.adresseService
            .query({filter: 'professeur-is-null'})
            .subscribe((res: HttpResponse<Adresse[]>) => {
                if (!this.professeur.adresseProfesseurId) {
                    this.adresseprofesseurs = res.body;
                } else {
                    this.adresseService
                        .find(this.professeur.adresseProfesseurId)
                        .subscribe((subRes: HttpResponse<Adresse>) => {
                            this.adresseprofesseurs = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.classeService.query()
            .subscribe((res: HttpResponse<Classe[]>) => { this.classes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.matiereService.query()
            .subscribe((res: HttpResponse<Matiere[]>) => { this.matieres = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.professeur.id !== undefined) {
            this.subscribeToSaveResponse(
                this.professeurService.update(this.professeur));
        } else {
            this.subscribeToSaveResponse(
                this.professeurService.create(this.professeur));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Professeur>>) {
        result.subscribe((res: HttpResponse<Professeur>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Professeur) {
        this.eventManager.broadcast({ name: 'professeurListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAdresseById(index: number, item: Adresse) {
        return item.id;
    }

    trackClasseById(index: number, item: Classe) {
        return item.id;
    }

    trackMatiereById(index: number, item: Matiere) {
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
    selector: 'jhi-professeur-popup',
    template: ''
})
export class ProfesseurPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private professeurPopupService: ProfesseurPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.professeurPopupService
                    .open(ProfesseurDialogComponent as Component, params['id']);
            } else {
                this.professeurPopupService
                    .open(ProfesseurDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
