import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IExamResult } from 'app/shared/model/exam-result.model';
import { AccountService } from 'app/core';
import { ExamResultService } from './exam-result.service';

@Component({
    selector: 'jhi-exam-result',
    templateUrl: './exam-result.component.html'
})
export class ExamResultComponent implements OnInit, OnDestroy {
    examResults: IExamResult[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected examResultService: ExamResultService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.examResultService
            .query()
            .pipe(
                filter((res: HttpResponse<IExamResult[]>) => res.ok),
                map((res: HttpResponse<IExamResult[]>) => res.body)
            )
            .subscribe(
                (res: IExamResult[]) => {
                    this.examResults = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInExamResults();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IExamResult) {
        return item.id;
    }

    registerChangeInExamResults() {
        this.eventSubscriber = this.eventManager.subscribe('examResultListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
