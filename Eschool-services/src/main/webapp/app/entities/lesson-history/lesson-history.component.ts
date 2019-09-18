import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILessonHistory } from 'app/shared/model/lesson-history.model';
import { AccountService } from 'app/core';
import { LessonHistoryService } from './lesson-history.service';

@Component({
    selector: 'jhi-lesson-history',
    templateUrl: './lesson-history.component.html'
})
export class LessonHistoryComponent implements OnInit, OnDestroy {
    lessonHistories: ILessonHistory[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected lessonHistoryService: LessonHistoryService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.lessonHistoryService
            .query()
            .pipe(
                filter((res: HttpResponse<ILessonHistory[]>) => res.ok),
                map((res: HttpResponse<ILessonHistory[]>) => res.body)
            )
            .subscribe(
                (res: ILessonHistory[]) => {
                    this.lessonHistories = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLessonHistories();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILessonHistory) {
        return item.id;
    }

    registerChangeInLessonHistories() {
        this.eventSubscriber = this.eventManager.subscribe('lessonHistoryListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
