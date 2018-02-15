import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoleSharedModule } from '../../shared';
import {
    ProfesseurService,
    ProfesseurPopupService,
    ProfesseurComponent,
    ProfesseurDetailComponent,
    ProfesseurDialogComponent,
    ProfesseurPopupComponent,
    ProfesseurDeletePopupComponent,
    ProfesseurDeleteDialogComponent,
    professeurRoute,
    professeurPopupRoute,
} from './';

const ENTITY_STATES = [
    ...professeurRoute,
    ...professeurPopupRoute,
];

@NgModule({
    imports: [
        EcoleSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ProfesseurComponent,
        ProfesseurDetailComponent,
        ProfesseurDialogComponent,
        ProfesseurDeleteDialogComponent,
        ProfesseurPopupComponent,
        ProfesseurDeletePopupComponent,
    ],
    entryComponents: [
        ProfesseurComponent,
        ProfesseurDialogComponent,
        ProfesseurPopupComponent,
        ProfesseurDeleteDialogComponent,
        ProfesseurDeletePopupComponent,
    ],
    providers: [
        ProfesseurService,
        ProfesseurPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EcoleProfesseurModule {}
