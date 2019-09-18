import { Moment } from 'moment';

export interface IAbscence {
    id?: number;
    date?: Moment;
    commentaire?: string;
    abscence?: boolean;
    justify?: boolean;
}

export class Abscence implements IAbscence {
    constructor(
        public id?: number,
        public date?: Moment,
        public commentaire?: string,
        public abscence?: boolean,
        public justify?: boolean
    ) {
        this.abscence = this.abscence || false;
        this.justify = this.justify || false;
    }
}
