import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CoursComponent } from './cours.component';
import { CoursDetailComponent } from './cours-detail.component';
import { CoursPopupComponent } from './cours-dialog.component';
import { CoursDeletePopupComponent } from './cours-delete-dialog.component';

export const coursRoute: Routes = [
    {
        path: 'cours',
        component: CoursComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.cours.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'cours/:id',
        component: CoursDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.cours.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const coursPopupRoute: Routes = [
    {
        path: 'cours-new',
        component: CoursPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.cours.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cours/:id/edit',
        component: CoursPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.cours.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cours/:id/delete',
        component: CoursDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.cours.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
