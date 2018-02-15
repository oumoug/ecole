import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Adresse } from './adresse.model';
import { AdressePopupService } from './adresse-popup.service';
import { AdresseService } from './adresse.service';

@Component({
    selector: 'jhi-adresse-dialog',
    templateUrl: './adresse-dialog.component.html'
})
export class AdresseDialogComponent implements OnInit {

    adresse: Adresse;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private adresseService: AdresseService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.adresse.id !== undefined) {
            this.subscribeToSaveResponse(
                this.adresseService.update(this.adresse));
        } else {
            this.subscribeToSaveResponse(
                this.adresseService.create(this.adresse));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Adresse>>) {
        result.subscribe((res: HttpResponse<Adresse>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Adresse) {
        this.eventManager.broadcast({ name: 'adresseListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-adresse-popup',
    template: ''
})
export class AdressePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private adressePopupService: AdressePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.adressePopupService
                    .open(AdresseDialogComponent as Component, params['id']);
            } else {
                this.adressePopupService
                    .open(AdresseDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
