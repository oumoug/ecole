import { BaseEntity } from './../../shared';

export class Cours implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public heure?: any,
        public coursProfesseurId?: number,
        public coursClassId?: number,
        public salleCoursId?: number,
    ) {
    }
}
