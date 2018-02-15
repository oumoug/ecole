import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Salle } from './salle.model';
import { SalleService } from './salle.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-salle',
    templateUrl: './salle.component.html'
})
export class SalleComponent implements OnInit, OnDestroy {
salles: Salle[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private salleService: SalleService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.salleService.query().subscribe(
            (res: HttpResponse<Salle[]>) => {
                this.salles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSalles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Salle) {
        return item.id;
    }
    registerChangeInSalles() {
        this.eventSubscriber = this.eventManager.subscribe('salleListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
