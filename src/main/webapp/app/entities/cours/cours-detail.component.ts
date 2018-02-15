import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Cours } from './cours.model';
import { CoursService } from './cours.service';

@Component({
    selector: 'jhi-cours-detail',
    templateUrl: './cours-detail.component.html'
})
export class CoursDetailComponent implements OnInit, OnDestroy {

    cours: Cours;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private coursService: CoursService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCours();
    }

    load(id) {
        this.coursService.find(id)
            .subscribe((coursResponse: HttpResponse<Cours>) => {
                this.cours = coursResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCours() {
        this.eventSubscriber = this.eventManager.subscribe(
            'coursListModification',
            (response) => this.load(this.cours.id)
        );
    }
}
