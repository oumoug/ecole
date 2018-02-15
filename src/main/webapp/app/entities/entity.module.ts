import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { EcoleEleveModule } from './eleve/eleve.module';
import { EcoleProfesseurModule } from './professeur/professeur.module';
import { EcoleClasseModule } from './classe/classe.module';
import { EcoleAdresseModule } from './adresse/adresse.module';
import { EcoleCoursModule } from './cours/cours.module';
import { EcoleMatiereModule } from './matiere/matiere.module';
import { EcoleSalleModule } from './salle/salle.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        EcoleEleveModule,
        EcoleProfesseurModule,
        EcoleClasseModule,
        EcoleAdresseModule,
        EcoleCoursModule,
        EcoleMatiereModule,
        EcoleSalleModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EcoleEntityModule {}
