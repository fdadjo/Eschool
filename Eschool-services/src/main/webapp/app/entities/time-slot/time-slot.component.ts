import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITimeSlot } from 'app/shared/model/time-slot.model';
import { AccountService } from 'app/core';
import { TimeSlotService } from './time-slot.service';

@Component({
    selector: 'jhi-time-slot',
    templateUrl: './time-slot.component.html'
})
export class TimeSlotComponent implements OnInit, OnDestroy {
    timeSlots: ITimeSlot[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected timeSlotService: TimeSlotService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.timeSlotService
            .query()
            .pipe(
                filter((res: HttpResponse<ITimeSlot[]>) => res.ok),
                map((res: HttpResponse<ITimeSlot[]>) => res.body)
            )
            .subscribe(
                (res: ITimeSlot[]) => {
                    this.timeSlots = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTimeSlots();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITimeSlot) {
        return item.id;
    }

    registerChangeInTimeSlots() {
        this.eventSubscriber = this.eventManager.subscribe('timeSlotListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
