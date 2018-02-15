import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcoleSharedModule } from '../../shared';
import {
    EleveService,
    ElevePopupService,
    EleveComponent,
    EleveDetailComponent,
    EleveDialogComponent,
    ElevePopupComponent,
    EleveDeletePopupComponent,
    EleveDeleteDialogComponent,
    eleveRoute,
    elevePopupRoute,
} from './';

const ENTITY_STATES = [
    ...eleveRoute,
    ...elevePopupRoute,
];

@NgModule({
    imports: [
        EcoleSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EleveComponent,
        EleveDetailComponent,
        EleveDialogComponent,
        EleveDeleteDialogComponent,
        ElevePopupComponent,
        EleveDeletePopupComponent,
    ],
    entryComponents: [
        EleveComponent,
        EleveDialogComponent,
        ElevePopupComponent,
        EleveDeleteDialogComponent,
        EleveDeletePopupComponent,
    ],
    providers: [
        EleveService,
        ElevePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EcoleEleveModule {}
