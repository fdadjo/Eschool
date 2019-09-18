export interface ITimeSlot {
    id?: number;
    keyName?: string;
}

export class TimeSlot implements ITimeSlot {
    constructor(public id?: number, public keyName?: string) {}
}
