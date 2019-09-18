export interface ILessonHistory {
    id?: number;
    courseName?: string;
    coef?: number;
}

export class LessonHistory implements ILessonHistory {
    constructor(public id?: number, public courseName?: string, public coef?: number) {}
}
