import { ILessonHistory } from 'app/shared/model/lesson-history.model';

export interface ILessonHistoryPreparation {
    id?: number;
    lessonHistory?: ILessonHistory;
}

export class LessonHistoryPreparation implements ILessonHistoryPreparation {
    constructor(public id?: number, public lessonHistory?: ILessonHistory) {}
}
