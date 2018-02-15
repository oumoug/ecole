import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Professeur } from './professeur.model';
import { ProfesseurService } from './professeur.service';

@Component({
    selector: 'jhi-professeur-detail',
    templateUrl: './professeur-detail.component.html'
})
export class ProfesseurDetailComponent implements OnInit, OnDestroy {

    professeur: Professeur;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private professeurService: ProfesseurService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProfesseurs();
    }

    load(id) {
        this.professeurService.find(id)
            .subscribe((professeurResponse: HttpResponse<Professeur>) => {
                this.professeur = professeurResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProfesseurs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'professeurListModification',
            (response) => this.load(this.professeur.id)
        );
    }
}
