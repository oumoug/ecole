import { BaseEntity } from './../../shared';

export class Salle implements BaseEntity {
    constructor(
        public id?: number,
        public nomSalle?: string,
        public numeroSalle?: number,
    ) {
    }
}
