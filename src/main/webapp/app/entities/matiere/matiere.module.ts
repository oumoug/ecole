import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoleSharedModule } from '../../shared';
import {
    MatiereService,
    MatierePopupService,
    MatiereComponent,
    MatiereDetailComponent,
    MatiereDialogComponent,
    MatierePopupComponent,
    MatiereDeletePopupComponent,
    MatiereDeleteDialogComponent,
    matiereRoute,
    matierePopupRoute,
    MatiereAddProfesseurDialogComponent,
    MatiereAddProfesseurPopupComponent,
} from './';


const ENTITY_STATES = [
    ...matiereRoute,
    ...matierePopupRoute,
];

@NgModule({
    imports: [
        EcoleSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MatiereComponent,
        MatiereDetailComponent,
        MatiereDialogComponent,
        MatiereDeleteDialogComponent,
        MatierePopupComponent,
        MatiereDeletePopupComponent,
        MatiereAddProfesseurDialogComponent,
        MatiereAddProfesseurPopupComponent,
    ],
    entryComponents: [
        MatiereComponent,
        MatiereDialogComponent,
        MatierePopupComponent,
        MatiereDeleteDialogComponent,
        MatiereDeletePopupComponent,
        MatiereAddProfesseurDialogComponent,
        MatiereAddProfesseurPopupComponent,
    ],
    providers: [
        MatiereService,
        MatierePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EcoleMatiereModule {}
