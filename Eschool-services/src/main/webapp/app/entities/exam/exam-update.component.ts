import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IExam } from 'app/shared/model/exam.model';
import { ExamService } from './exam.service';

@Component({
    selector: 'jhi-exam-update',
    templateUrl: './exam-update.component.html'
})
export class ExamUpdateComponent implements OnInit {
    exam: IExam;
    isSaving: boolean;
    plannedOn: string;

    constructor(protected examService: ExamService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ exam }) => {
            this.exam = exam;
            this.plannedOn = this.exam.plannedOn != null ? this.exam.plannedOn.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.exam.plannedOn = this.plannedOn != null ? moment(this.plannedOn, DATE_TIME_FORMAT) : null;
        if (this.exam.id !== undefined) {
            this.subscribeToSaveResponse(this.examService.update(this.exam));
        } else {
            this.subscribeToSaveResponse(this.examService.create(this.exam));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IExam>>) {
        result.subscribe((res: HttpResponse<IExam>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
