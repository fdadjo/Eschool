import { IClassroom } from 'app/shared/model/classroom.model';

export interface IClassroomHistory {
    id?: number;
    fees?: number;
    classroom?: IClassroom;
}

export class ClassroomHistory implements IClassroomHistory {
    constructor(public id?: number, public fees?: number, public classroom?: IClassroom) {}
}
