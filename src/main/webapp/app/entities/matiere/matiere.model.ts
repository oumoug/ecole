import { BaseEntity } from './../../shared';

export class Matiere implements BaseEntity {
    constructor(
        public id?: number,
        public nomMatiere?: string,
        public matiereProfs?: BaseEntity[],
        public matiereEleves?: BaseEntity[],
    ) {
    }
}
