import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoleSharedModule } from '../../shared';
import {
    AdresseService,
    AdressePopupService,
    AdresseComponent,
    AdresseDetailComponent,
    AdresseDialogComponent,
    AdressePopupComponent,
    AdresseDeletePopupComponent,
    AdresseDeleteDialogComponent,
    adresseRoute,
    adressePopupRoute,
} from './';

const ENTITY_STATES = [
    ...adresseRoute,
    ...adressePopupRoute,
];

@NgModule({
    imports: [
        EcoleSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AdresseComponent,
        AdresseDetailComponent,
        AdresseDialogComponent,
        AdresseDeleteDialogComponent,
        AdressePopupComponent,
        AdresseDeletePopupComponent,
    ],
    entryComponents: [
        AdresseComponent,
        AdresseDialogComponent,
        AdressePopupComponent,
        AdresseDeleteDialogComponent,
        AdresseDeletePopupComponent,
    ],
    providers: [
        AdresseService,
        AdressePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EcoleAdresseModule {}
