import { Moment } from 'moment';

export interface IExam {
    id?: number;
    name?: string;
    coef?: number;
    value?: number;
    session?: string;
    examinationFile?: string;
    plannedOn?: Moment;
}

export class Exam implements IExam {
    constructor(
        public id?: number,
        public name?: string,
        public coef?: number,
        public value?: number,
        public session?: string,
        public examinationFile?: string,
        public plannedOn?: Moment
    ) {}
}
