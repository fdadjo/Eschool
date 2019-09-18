export interface IJob {
    id?: number;
    title?: string;
    description?: string;
    minSalary?: number;
    maxSalary?: number;
}

export class Job implements IJob {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public minSalary?: number,
        public maxSalary?: number
    ) {}
}
