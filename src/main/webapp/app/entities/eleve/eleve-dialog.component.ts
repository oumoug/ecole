import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Eleve } from './eleve.model';
import { ElevePopupService } from './eleve-popup.service';
import { EleveService } from './eleve.service';
import { Adresse, AdresseService } from '../adresse';
import { Classe, ClasseService } from '../classe';

@Component({
    selector: 'jhi-eleve-dialog',
    templateUrl: './eleve-dialog.component.html'
})
export class EleveDialogComponent implements OnInit {

    eleve: Eleve;
    isSaving: boolean;

    adresseeleves: Adresse[];

    eleveclasses: Classe[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private eleveService: EleveService,
        private adresseService: AdresseService,
        private classeService: ClasseService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.adresseService
            .query({filter: 'eleve-is-null'})
            .subscribe((res: HttpResponse<Adresse[]>) => {
                if (!this.eleve.adresseEleveId) {
                    this.adresseeleves = res.body;
                } else {
                    this.adresseService
                        .find(this.eleve.adresseEleveId)
                        .subscribe((subRes: HttpResponse<Adresse>) => {
                            this.adresseeleves = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.classeService
            .query({filter: 'eleve-is-null'})
            .subscribe((res: HttpResponse<Classe[]>) => {
                if (!this.eleve.eleveClasseId) {
                    this.eleveclasses = res.body;
                } else {
                    this.classeService
                        .find(this.eleve.eleveClasseId)
                        .subscribe((subRes: HttpResponse<Classe>) => {
                            this.eleveclasses = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.eleve.id !== undefined) {
            this.subscribeToSaveResponse(
                this.eleveService.update(this.eleve));
        } else {
            this.subscribeToSaveResponse(
                this.eleveService.create(this.eleve));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Eleve>>) {
        result.subscribe((res: HttpResponse<Eleve>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Eleve) {
        this.eventManager.broadcast({ name: 'eleveListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-eleve-popup',
    template: ''
})
export class ElevePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private elevePopupService: ElevePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.elevePopupService
                    .open(EleveDialogComponent as Component, params['id']);
            } else {
                this.elevePopupService
                    .open(EleveDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
