import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IClassroomHistory } from 'app/shared/model/classroom-history.model';
import { AccountService } from 'app/core';
import { ClassroomHistoryService } from './classroom-history.service';

@Component({
    selector: 'jhi-classroom-history',
    templateUrl: './classroom-history.component.html'
})
export class ClassroomHistoryComponent implements OnInit, OnDestroy {
    classroomHistories: IClassroomHistory[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected classroomHistoryService: ClassroomHistoryService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.classroomHistoryService
            .query()
            .pipe(
                filter((res: HttpResponse<IClassroomHistory[]>) => res.ok),
                map((res: HttpResponse<IClassroomHistory[]>) => res.body)
            )
            .subscribe(
                (res: IClassroomHistory[]) => {
                    this.classroomHistories = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInClassroomHistories();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IClassroomHistory) {
        return item.id;
    }

    registerChangeInClassroomHistories() {
        this.eventSubscriber = this.eventManager.subscribe('classroomHistoryListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
