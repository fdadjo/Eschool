export interface IJobHistory {
    id?: number;
    monthlySalary?: number;
}

export class JobHistory implements IJobHistory {
    constructor(public id?: number, public monthlySalary?: number) {}
}
