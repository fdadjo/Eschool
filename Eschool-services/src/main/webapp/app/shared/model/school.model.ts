export interface ISchool {
    id?: number;
    schoolName?: string;
    streetAddress?: string;
    postalCode?: string;
    city?: string;
    stateProvince?: string;
    telephone?: string;
    devise?: string;
    description?: string;
    ccCoef?: number;
    cCoef?: number;
    logoLink?: string;
    countryname?: string;
    countryMotto?: string;
    countrySecretary?: string;
    countrySubSecretary?: string;
    schoolMotto?: string;
}

export class School implements ISchool {
    constructor(
        public id?: number,
        public schoolName?: string,
        public streetAddress?: string,
        public postalCode?: string,
        public city?: string,
        public stateProvince?: string,
        public telephone?: string,
        public devise?: string,
        public description?: string,
        public ccCoef?: number,
        public cCoef?: number,
        public logoLink?: string,
        public countryname?: string,
        public countryMotto?: string,
        public countrySecretary?: string,
        public countrySubSecretary?: string,
        public schoolMotto?: string
    ) {}
}
