import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ClasseComponent } from './classe.component';
import { ClasseDetailComponent } from './classe-detail.component';
import { ClassePopupComponent } from './classe-dialog.component';
import { ClasseDeletePopupComponent } from './classe-delete-dialog.component';
import { ClasseAddmatierePopupComponent}  from './classe-addmatiere-dialog.component';


export const classeRoute: Routes = [
    {
        path: 'classe',
        component: ClasseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.classe.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'classe/:id',
        component: ClasseDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.classe.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const classePopupRoute: Routes = [
    {
        path: 'classe-new',
        component: ClassePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.classe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'classe/:id/edit',
        component: ClassePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.classe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'classe/:id/delete',
        component: ClasseDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.classe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'classe/:id/addMatiere',
        component:ClasseAddmatierePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ecoleApp.classe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
    
];
