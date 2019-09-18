import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITimeSheet } from 'app/shared/model/time-sheet.model';
import { TimeSheetService } from './time-sheet.service';
import { IClassroom } from 'app/shared/model/classroom.model';
import { ClassroomService } from 'app/entities/classroom';

@Component({
    selector: 'jhi-time-sheet-update',
    templateUrl: './time-sheet-update.component.html'
})
export class TimeSheetUpdateComponent implements OnInit {
    timeSheet: ITimeSheet;
    isSaving: boolean;

    classrooms: IClassroom[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected timeSheetService: TimeSheetService,
        protected classroomService: ClassroomService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ timeSheet }) => {
            this.timeSheet = timeSheet;
        });
        this.classroomService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IClassroom[]>) => mayBeOk.ok),
                map((response: HttpResponse<IClassroom[]>) => response.body)
            )
            .subscribe((res: IClassroom[]) => (this.classrooms = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.timeSheet.id !== undefined) {
            this.subscribeToSaveResponse(this.timeSheetService.update(this.timeSheet));
        } else {
            this.subscribeToSaveResponse(this.timeSheetService.create(this.timeSheet));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITimeSheet>>) {
        result.subscribe((res: HttpResponse<ITimeSheet>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackClassroomById(index: number, item: IClassroom) {
        return item.id;
    }
}
