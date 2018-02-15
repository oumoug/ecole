import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AdresseComponent } from './adresse.component';
import { AdresseDetailComponent } from './adresse-detail.component';
import { AdressePopupComponent } from './adresse-dialog.component';
import { AdresseDeletePopupComponent } from './adresse-delete-dialog.component';

export const adresseRoute: Routes = [
    {
        path: 'adresse',
        component: AdresseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.adresse.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'adresse/:id',
        component: AdresseDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.adresse.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const adressePopupRoute: Routes = [
    {
        path: 'adresse-new',
        component: AdressePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.adresse.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'adresse/:id/edit',
        component: AdressePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.adresse.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'adresse/:id/delete',
        component: AdresseDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.adresse.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
