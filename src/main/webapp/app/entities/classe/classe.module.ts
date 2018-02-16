import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoleSharedModule } from '../../shared';
import {
    ClasseService,
    ClassePopupService,
    ClasseComponent,
    ClasseDetailComponent,
    ClasseDialogComponent,
    ClassePopupComponent,
    ClasseDeletePopupComponent,
    ClasseDeleteDialogComponent,
    classeRoute,
    classePopupRoute,
    ClasseAddmatierePopupComponent,
    ClasseAddmatiereDialogComponent ,
} from './';
const ENTITY_STATES = [
    ...classeRoute,
    ...classePopupRoute,
];

@NgModule({
    imports: [
        EcoleSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ClasseComponent,
        ClasseDetailComponent,
        ClasseDialogComponent,
        ClasseDeleteDialogComponent,
        ClassePopupComponent,
        ClasseDeletePopupComponent,
        ClasseAddmatierePopupComponent,
        ClasseAddmatiereDialogComponent,
    ],
    entryComponents: [
        ClasseComponent,
        ClasseDialogComponent,
        ClassePopupComponent,
        ClasseDeleteDialogComponent,
        ClasseDeletePopupComponent,
        ClasseAddmatierePopupComponent,
        ClasseAddmatiereDialogComponent ,
    ],
    providers: [
        ClasseService,
        ClassePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EcoleClasseModule {}
