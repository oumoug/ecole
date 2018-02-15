import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ProfesseurComponent } from './professeur.component';
import { ProfesseurDetailComponent } from './professeur-detail.component';
import { ProfesseurPopupComponent } from './professeur-dialog.component';
import { ProfesseurDeletePopupComponent } from './professeur-delete-dialog.component';

export const professeurRoute: Routes = [
    {
        path: 'professeur',
        component: ProfesseurComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.professeur.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'professeur/:id',
        component: ProfesseurDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.professeur.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const professeurPopupRoute: Routes = [
    {
        path: 'professeur-new',
        component: ProfesseurPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.professeur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'professeur/:id/edit',
        component: ProfesseurPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.professeur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'professeur/:id/delete',
        component: ProfesseurDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.professeur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
