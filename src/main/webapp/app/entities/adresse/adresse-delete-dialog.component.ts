import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Adresse } from './adresse.model';
import { AdressePopupService } from './adresse-popup.service';
import { AdresseService } from './adresse.service';

@Component({
    selector: 'jhi-adresse-delete-dialog',
    templateUrl: './adresse-delete-dialog.component.html'
})
export class AdresseDeleteDialogComponent {

    adresse: Adresse;

    constructor(
        private adresseService: AdresseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.adresseService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'adresseListModification',
                content: 'Deleted an adresse'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-adresse-delete-popup',
    template: ''
})
export class AdresseDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private adressePopupService: AdressePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.adressePopupService
                .open(AdresseDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
