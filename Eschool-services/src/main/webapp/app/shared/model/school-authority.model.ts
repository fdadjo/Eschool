import { ISchool } from 'app/shared/model/school.model';
import { IUser } from 'app/core/user/user.model';
import { IAuthority } from 'app/shared/model/authority.model';

export interface ISchoolAuthority {
    id?: number;
    school?: ISchool;
    user?: IUser;
    role?: IAuthority;
}

export class SchoolAuthority implements ISchoolAuthority {
    constructor(public id?: number, public school?: ISchool, public user?: IUser, public role?: IAuthority) {}
}
