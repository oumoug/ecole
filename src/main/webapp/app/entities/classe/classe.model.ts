import { BaseEntity } from './../../shared';

export class Classe implements BaseEntity {
    constructor(
        public id?: number,
        public nomClass?: string,
        public matiereEtudiers?: BaseEntity[],
        public professeurClasses?: BaseEntity[],
    ) {
    }
}
