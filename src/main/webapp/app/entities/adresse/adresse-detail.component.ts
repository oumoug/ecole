import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Adresse } from './adresse.model';
import { AdresseService } from './adresse.service';

@Component({
    selector: 'jhi-adresse-detail',
    templateUrl: './adresse-detail.component.html'
})
export class AdresseDetailComponent implements OnInit, OnDestroy {

    adresse: Adresse;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private adresseService: AdresseService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAdresses();
    }

    load(id) {
        this.adresseService.find(id)
            .subscribe((adresseResponse: HttpResponse<Adresse>) => {
                this.adresse = adresseResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAdresses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'adresseListModification',
            (response) => this.load(this.adresse.id)
        );
    }
}
