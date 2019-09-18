import { IUser } from 'app/core/user/user.model';
import { IClassroom } from 'app/shared/model/classroom.model';
import { ISchool } from 'app/shared/model/school.model';

export const enum PaymentType {
    COMMA = 'COMMA'
}

export interface IPayment {
    id?: number;
    amount?: number;
    type?: PaymentType;
    user?: IUser;
    classroom?: IClassroom;
    school?: ISchool;
}

export class Payment implements IPayment {
    constructor(
        public id?: number,
        public amount?: number,
        public type?: PaymentType,
        public user?: IUser,
        public classroom?: IClassroom,
        public school?: ISchool
    ) {}
}
