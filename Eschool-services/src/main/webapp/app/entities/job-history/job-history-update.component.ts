import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IJobHistory } from 'app/shared/model/job-history.model';
import { JobHistoryService } from './job-history.service';

@Component({
    selector: 'jhi-job-history-update',
    templateUrl: './job-history-update.component.html'
})
export class JobHistoryUpdateComponent implements OnInit {
    jobHistory: IJobHistory;
    isSaving: boolean;

    constructor(protected jobHistoryService: JobHistoryService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ jobHistory }) => {
            this.jobHistory = jobHistory;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.jobHistory.id !== undefined) {
            this.subscribeToSaveResponse(this.jobHistoryService.update(this.jobHistory));
        } else {
            this.subscribeToSaveResponse(this.jobHistoryService.create(this.jobHistory));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobHistory>>) {
        result.subscribe((res: HttpResponse<IJobHistory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
