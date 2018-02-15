import { BaseEntity } from './../../shared';

export class Adresse implements BaseEntity {
    constructor(
        public id?: number,
        public codePostal?: number,
        public ville?: string,
        public numeroRue?: number,
    ) {
    }
}
