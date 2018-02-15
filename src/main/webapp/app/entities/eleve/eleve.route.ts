import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EleveComponent } from './eleve.component';
import { EleveDetailComponent } from './eleve-detail.component';
import { ElevePopupComponent } from './eleve-dialog.component';
import { EleveDeletePopupComponent } from './eleve-delete-dialog.component';

export const eleveRoute: Routes = [
    {
        path: 'eleve',
        component: EleveComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.eleve.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'eleve/:id',
        component: EleveDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.eleve.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const elevePopupRoute: Routes = [
    {
        path: 'eleve-new',
        component: ElevePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.eleve.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'eleve/:id/edit',
        component: ElevePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.eleve.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'eleve/:id/delete',
        component: EleveDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.eleve.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
