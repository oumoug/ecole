import { BaseEntity } from './../../shared';

export class Eleve implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public prenom?: string,
        public moyenne?: number,
        public adresseEleveId?: number,
        public eleveClasseId?: number,
    ) {
    }
}
