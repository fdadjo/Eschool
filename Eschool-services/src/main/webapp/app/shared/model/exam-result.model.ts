export interface IExamResult {
    id?: number;
    name?: string;
}

export class ExamResult implements IExamResult {
    constructor(public id?: number, public name?: string) {}
}
