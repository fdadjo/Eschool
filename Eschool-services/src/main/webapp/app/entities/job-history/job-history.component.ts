import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IJobHistory } from 'app/shared/model/job-history.model';
import { AccountService } from 'app/core';
import { JobHistoryService } from './job-history.service';

@Component({
    selector: 'jhi-job-history',
    templateUrl: './job-history.component.html'
})
export class JobHistoryComponent implements OnInit, OnDestroy {
    jobHistories: IJobHistory[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected jobHistoryService: JobHistoryService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.jobHistoryService
            .query()
            .pipe(
                filter((res: HttpResponse<IJobHistory[]>) => res.ok),
                map((res: HttpResponse<IJobHistory[]>) => res.body)
            )
            .subscribe(
                (res: IJobHistory[]) => {
                    this.jobHistories = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInJobHistories();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IJobHistory) {
        return item.id;
    }

    registerChangeInJobHistories() {
        this.eventSubscriber = this.eventManager.subscribe('jobHistoryListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
