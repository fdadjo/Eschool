import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IExamResult } from 'app/shared/model/exam-result.model';
import { ExamResultService } from './exam-result.service';

@Component({
    selector: 'jhi-exam-result-update',
    templateUrl: './exam-result-update.component.html'
})
export class ExamResultUpdateComponent implements OnInit {
    examResult: IExamResult;
    isSaving: boolean;

    constructor(protected examResultService: ExamResultService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ examResult }) => {
            this.examResult = examResult;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.examResult.id !== undefined) {
            this.subscribeToSaveResponse(this.examResultService.update(this.examResult));
        } else {
            this.subscribeToSaveResponse(this.examResultService.create(this.examResult));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IExamResult>>) {
        result.subscribe((res: HttpResponse<IExamResult>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
