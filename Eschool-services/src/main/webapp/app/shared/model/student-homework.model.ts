import { IUser } from 'app/core/user/user.model';
import { IHomework } from 'app/shared/model/homework.model';

export interface IStudentHomework {
    id?: number;
    done?: boolean;
    student?: IUser;
    homework?: IHomework;
}

export class StudentHomework implements IStudentHomework {
    constructor(public id?: number, public done?: boolean, public student?: IUser, public homework?: IHomework) {
        this.done = this.done || false;
    }
}
