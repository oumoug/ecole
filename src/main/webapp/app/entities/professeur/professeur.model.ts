import { BaseEntity } from './../../shared';

export class Professeur implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public prenom?: string,
        public adresseProfesseurId?: number,
        public classeEnseignes?: BaseEntity[],
        public matiereEnseignes?: BaseEntity[],
    ) {
    }
}
