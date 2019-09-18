import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITimeSlotHistory } from 'app/shared/model/time-slot-history.model';

@Component({
    selector: 'jhi-time-slot-history-detail',
    templateUrl: './time-slot-history-detail.component.html'
})
export class TimeSlotHistoryDetailComponent implements OnInit {
    timeSlotHistory: ITimeSlotHistory;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ timeSlotHistory }) => {
            this.timeSlotHistory = timeSlotHistory;
        });
    }

    previousState() {
        window.history.back();
    }
}
