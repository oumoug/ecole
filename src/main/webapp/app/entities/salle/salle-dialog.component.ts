import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Salle } from './salle.model';
import { SallePopupService } from './salle-popup.service';
import { SalleService } from './salle.service';

@Component({
    selector: 'jhi-salle-dialog',
    templateUrl: './salle-dialog.component.html'
})
export class SalleDialogComponent implements OnInit {

    salle: Salle;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private salleService: SalleService,
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
        if (this.salle.id !== undefined) {
            this.subscribeToSaveResponse(
                this.salleService.update(this.salle));
        } else {
            this.subscribeToSaveResponse(
                this.salleService.create(this.salle));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Salle>>) {
        result.subscribe((res: HttpResponse<Salle>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Salle) {
        this.eventManager.broadcast({ name: 'salleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-salle-popup',
    template: ''
})
export class SallePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sallePopupService: SallePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sallePopupService
                    .open(SalleDialogComponent as Component, params['id']);
            } else {
                this.sallePopupService
                    .open(SalleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
