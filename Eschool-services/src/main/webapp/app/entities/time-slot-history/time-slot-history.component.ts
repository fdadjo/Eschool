import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITimeSlotHistory } from 'app/shared/model/time-slot-history.model';
import { AccountService } from 'app/core';
import { TimeSlotHistoryService } from './time-slot-history.service';

@Component({
    selector: 'jhi-time-slot-history',
    templateUrl: './time-slot-history.component.html'
})
export class TimeSlotHistoryComponent implements OnInit, OnDestroy {
    timeSlotHistories: ITimeSlotHistory[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected timeSlotHistoryService: TimeSlotHistoryService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.timeSlotHistoryService
            .query()
            .pipe(
                filter((res: HttpResponse<ITimeSlotHistory[]>) => res.ok),
                map((res: HttpResponse<ITimeSlotHistory[]>) => res.body)
            )
            .subscribe(
                (res: ITimeSlotHistory[]) => {
                    this.timeSlotHistories = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTimeSlotHistories();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITimeSlotHistory) {
        return item.id;
    }

    registerChangeInTimeSlotHistories() {
        this.eventSubscriber = this.eventManager.subscribe('timeSlotHistoryListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
