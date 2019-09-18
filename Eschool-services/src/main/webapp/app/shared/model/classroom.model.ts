export interface IClassroom {
    id?: number;
    classroomName?: string;
    year?: number;
    tutionFees?: number;
    ccCoef?: number;
    cCoef?: number;
}

export class Classroom implements IClassroom {
    constructor(
        public id?: number,
        public classroomName?: string,
        public year?: number,
        public tutionFees?: number,
        public ccCoef?: number,
        public cCoef?: number
    ) {}
}
