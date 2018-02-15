import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Adresse } from './adresse.model';
import { AdresseService } from './adresse.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-adresse',
    templateUrl: './adresse.component.html'
})
export class AdresseComponent implements OnInit, OnDestroy {
adresses: Adresse[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private adresseService: AdresseService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.adresseService.query().subscribe(
            (res: HttpResponse<Adresse[]>) => {
                this.adresses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAdresses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Adresse) {
        return item.id;
    }
    registerChangeInAdresses() {
        this.eventSubscriber = this.eventManager.subscribe('adresseListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
