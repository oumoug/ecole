import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Professeur } from './professeur.model';
import { ProfesseurPopupService } from './professeur-popup.service';
import { ProfesseurService } from './professeur.service';

@Component({
    selector: 'jhi-professeur-delete-dialog',
    templateUrl: './professeur-delete-dialog.component.html'
})
export class ProfesseurDeleteDialogComponent {

    professeur: Professeur;

    constructor(
        private professeurService: ProfesseurService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.professeurService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'professeurListModification',
                content: 'Deleted an professeur'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-professeur-delete-popup',
    template: ''
})
export class ProfesseurDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private professeurPopupService: ProfesseurPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.professeurPopupService
                .open(ProfesseurDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
