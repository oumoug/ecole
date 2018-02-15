import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Cours } from './cours.model';
import { CoursService } from './cours.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-cours',
    templateUrl: './cours.component.html'
})
export class CoursComponent implements OnInit, OnDestroy {
cours: Cours[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private coursService: CoursService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.coursService.query().subscribe(
            (res: HttpResponse<Cours[]>) => {
                this.cours = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCours();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Cours) {
        return item.id;
    }
    registerChangeInCours() {
        this.eventSubscriber = this.eventManager.subscribe('coursListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
