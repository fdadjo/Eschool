import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ILessonHistory } from 'app/shared/model/lesson-history.model';
import { LessonHistoryService } from './lesson-history.service';

@Component({
    selector: 'jhi-lesson-history-update',
    templateUrl: './lesson-history-update.component.html'
})
export class LessonHistoryUpdateComponent implements OnInit {
    lessonHistory: ILessonHistory;
    isSaving: boolean;

    constructor(protected lessonHistoryService: LessonHistoryService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lessonHistory }) => {
            this.lessonHistory = lessonHistory;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.lessonHistory.id !== undefined) {
            this.subscribeToSaveResponse(this.lessonHistoryService.update(this.lessonHistory));
        } else {
            this.subscribeToSaveResponse(this.lessonHistoryService.create(this.lessonHistory));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILessonHistory>>) {
        result.subscribe((res: HttpResponse<ILessonHistory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
