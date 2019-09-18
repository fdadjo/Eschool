import { ITimesheet } from 'app/shared/model/timesheet.model';

export interface ITimeSlotHistory {
    id?: number;
    timesheet?: ITimesheet;
}

export class TimeSlotHistory implements ITimeSlotHistory {
    constructor(public id?: number, public timesheet?: ITimesheet) {}
}
