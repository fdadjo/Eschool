import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITimeSheet } from 'app/shared/model/time-sheet.model';
import { AccountService } from 'app/core';
import { TimeSheetService } from './time-sheet.service';

@Component({
    selector: 'jhi-time-sheet',
    templateUrl: './time-sheet.component.html'
})
export class TimeSheetComponent implements OnInit, OnDestroy {
    timeSheets: ITimeSheet[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected timeSheetService: TimeSheetService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.timeSheetService
            .query()
            .pipe(
                filter((res: HttpResponse<ITimeSheet[]>) => res.ok),
                map((res: HttpResponse<ITimeSheet[]>) => res.body)
            )
            .subscribe(
                (res: ITimeSheet[]) => {
                    this.timeSheets = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTimeSheets();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITimeSheet) {
        return item.id;
    }

    registerChangeInTimeSheets() {
        this.eventSubscriber = this.eventManager.subscribe('timeSheetListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
