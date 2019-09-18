import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IClassroomHistory } from 'app/shared/model/classroom-history.model';
import { ClassroomHistoryService } from './classroom-history.service';
import { IClassroom } from 'app/shared/model/classroom.model';
import { ClassroomService } from 'app/entities/classroom';

@Component({
    selector: 'jhi-classroom-history-update',
    templateUrl: './classroom-history-update.component.html'
})
export class ClassroomHistoryUpdateComponent implements OnInit {
    classroomHistory: IClassroomHistory;
    isSaving: boolean;

    classrooms: IClassroom[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected classroomHistoryService: ClassroomHistoryService,
        protected classroomService: ClassroomService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ classroomHistory }) => {
            this.classroomHistory = classroomHistory;
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
        if (this.classroomHistory.id !== undefined) {
            this.subscribeToSaveResponse(this.classroomHistoryService.update(this.classroomHistory));
        } else {
            this.subscribeToSaveResponse(this.classroomHistoryService.create(this.classroomHistory));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IClassroomHistory>>) {
        result.subscribe((res: HttpResponse<IClassroomHistory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
