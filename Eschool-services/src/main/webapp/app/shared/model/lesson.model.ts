export interface ILesson {
    id?: number;
    title?: string;
    description?: string;
    tagKey?: string;
}

export class Lesson implements ILesson {
    constructor(public id?: number, public title?: string, public description?: string, public tagKey?: string) {}
}
