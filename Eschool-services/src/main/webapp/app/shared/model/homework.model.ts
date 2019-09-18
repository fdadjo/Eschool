export interface IHomework {
    id?: number;
    name?: string;
    description?: string;
    fileUrl?: string;
}

export class Homework implements IHomework {
    constructor(public id?: number, public name?: string, public description?: string, public fileUrl?: string) {}
}
