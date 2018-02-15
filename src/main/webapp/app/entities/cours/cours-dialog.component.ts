import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Cours } from './cours.model';
import { CoursPopupService } from './cours-popup.service';
import { CoursService } from './cours.service';
import { Professeur, ProfesseurService } from '../professeur';
import { Classe, ClasseService } from '../classe';
import { Salle, SalleService } from '../salle';

@Component({
    selector: 'jhi-cours-dialog',
    templateUrl: './cours-dialog.component.html'
})
export class CoursDialogComponent implements OnInit {

    cours: Cours;
    isSaving: boolean;

    coursprofesseurs: Professeur[];

    coursclasses: Classe[];

    sallecours: Salle[];
    dateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private coursService: CoursService,
        private professeurService: ProfesseurService,
        private classeService: ClasseService,
        private salleService: SalleService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.professeurService
            .query({filter: 'cours-is-null'})
            .subscribe((res: HttpResponse<Professeur[]>) => {
                if (!this.cours.coursProfesseurId) {
                    this.coursprofesseurs = res.body;
                } else {
                    this.professeurService
                        .find(this.cours.coursProfesseurId)
                        .subscribe((subRes: HttpResponse<Professeur>) => {
                            this.coursprofesseurs = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.classeService
            .query({filter: 'cours-is-null'})
            .subscribe((res: HttpResponse<Classe[]>) => {
                if (!this.cours.coursClassId) {
                    this.coursclasses = res.body;
                } else {
                    this.classeService
                        .find(this.cours.coursClassId)
                        .subscribe((subRes: HttpResponse<Classe>) => {
                            this.coursclasses = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.salleService
            .query({filter: 'cours-is-null'})
            .subscribe((res: HttpResponse<Salle[]>) => {
                if (!this.cours.salleCoursId) {
                    this.sallecours = res.body;
                } else {
                    this.salleService
                        .find(this.cours.salleCoursId)
                        .subscribe((subRes: HttpResponse<Salle>) => {
                            this.sallecours = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.cours.id !== undefined) {
            this.subscribeToSaveResponse(
                this.coursService.update(this.cours));
        } else {
            this.subscribeToSaveResponse(
                this.coursService.create(this.cours));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Cours>>) {
        result.subscribe((res: HttpResponse<Cours>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Cours) {
        this.eventManager.broadcast({ name: 'coursListModification', content: 'OK'});
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

    trackSalleById(index: number, item: Salle) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-cours-popup',
    template: ''
})
export class CoursPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private coursPopupService: CoursPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.coursPopupService
                    .open(CoursDialogComponent as Component, params['id']);
            } else {
                this.coursPopupService
                    .open(CoursDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
