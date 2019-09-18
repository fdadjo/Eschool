import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITimeSlot } from 'app/shared/model/time-slot.model';
import { TimeSlotService } from './time-slot.service';

@Component({
    selector: 'jhi-time-slot-update',
    templateUrl: './time-slot-update.component.html'
})
export class TimeSlotUpdateComponent implements OnInit {
    timeSlot: ITimeSlot;
    isSaving: boolean;

    constructor(protected timeSlotService: TimeSlotService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ timeSlot }) => {
            this.timeSlot = timeSlot;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.timeSlot.id !== undefined) {
            this.subscribeToSaveResponse(this.timeSlotService.update(this.timeSlot));
        } else {
            this.subscribeToSaveResponse(this.timeSlotService.create(this.timeSlot));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITimeSlot>>) {
        result.subscribe((res: HttpResponse<ITimeSlot>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
