import { IClassroom } from 'app/shared/model/classroom.model';

export interface ITimeSheet {
    id?: number;
    name?: string;
    classroom?: IClassroom;
}

export class TimeSheet implements ITimeSheet {
    constructor(public id?: number, public name?: string, public classroom?: IClassroom) {}
}
