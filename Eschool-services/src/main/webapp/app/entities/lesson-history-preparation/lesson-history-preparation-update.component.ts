import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ILessonHistoryPreparation } from 'app/shared/model/lesson-history-preparation.model';
import { LessonHistoryPreparationService } from './lesson-history-preparation.service';
import { ILessonHistory } from 'app/shared/model/lesson-history.model';
import { LessonHistoryService } from 'app/entities/lesson-history';

@Component({
    selector: 'jhi-lesson-history-preparation-update',
    templateUrl: './lesson-history-preparation-update.component.html'
})
export class LessonHistoryPreparationUpdateComponent implements OnInit {
    lessonHistoryPreparation: ILessonHistoryPreparation;
    isSaving: boolean;

    lessonhistories: ILessonHistory[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected lessonHistoryPreparationService: LessonHistoryPreparationService,
        protected lessonHistoryService: LessonHistoryService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lessonHistoryPreparation }) => {
            this.lessonHistoryPreparation = lessonHistoryPreparation;
        });
        this.lessonHistoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ILessonHistory[]>) => mayBeOk.ok),
                map((response: HttpResponse<ILessonHistory[]>) => response.body)
            )
            .subscribe((res: ILessonHistory[]) => (this.lessonhistories = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.lessonHistoryPreparation.id !== undefined) {
            this.subscribeToSaveResponse(this.lessonHistoryPreparationService.update(this.lessonHistoryPreparation));
        } else {
            this.subscribeToSaveResponse(this.lessonHistoryPreparationService.create(this.lessonHistoryPreparation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILessonHistoryPreparation>>) {
        result.subscribe(
            (res: HttpResponse<ILessonHistoryPreparation>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackLessonHistoryById(index: number, item: ILessonHistory) {
        return item.id;
    }
}
