import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITimeSlotHistory } from 'app/shared/model/time-slot-history.model';
import { TimeSlotHistoryService } from './time-slot-history.service';
import { ITimesheet } from 'app/shared/model/timesheet.model';
import { TimesheetService } from 'app/entities/timesheet';

@Component({
    selector: 'jhi-time-slot-history-update',
    templateUrl: './time-slot-history-update.component.html'
})
export class TimeSlotHistoryUpdateComponent implements OnInit {
    timeSlotHistory: ITimeSlotHistory;
    isSaving: boolean;

    timesheets: ITimesheet[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected timeSlotHistoryService: TimeSlotHistoryService,
        protected timesheetService: TimesheetService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ timeSlotHistory }) => {
            this.timeSlotHistory = timeSlotHistory;
        });
        this.timesheetService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITimesheet[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITimesheet[]>) => response.body)
            )
            .subscribe((res: ITimesheet[]) => (this.timesheets = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.timeSlotHistory.id !== undefined) {
            this.subscribeToSaveResponse(this.timeSlotHistoryService.update(this.timeSlotHistory));
        } else {
            this.subscribeToSaveResponse(this.timeSlotHistoryService.create(this.timeSlotHistory));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITimeSlotHistory>>) {
        result.subscribe((res: HttpResponse<ITimeSlotHistory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackTimesheetById(index: number, item: ITimesheet) {
        return item.id;
    }
}
