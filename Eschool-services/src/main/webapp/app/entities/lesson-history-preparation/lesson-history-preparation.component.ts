import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILessonHistoryPreparation } from 'app/shared/model/lesson-history-preparation.model';
import { AccountService } from 'app/core';
import { LessonHistoryPreparationService } from './lesson-history-preparation.service';

@Component({
    selector: 'jhi-lesson-history-preparation',
    templateUrl: './lesson-history-preparation.component.html'
})
export class LessonHistoryPreparationComponent implements OnInit, OnDestroy {
    lessonHistoryPreparations: ILessonHistoryPreparation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected lessonHistoryPreparationService: LessonHistoryPreparationService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.lessonHistoryPreparationService
            .query()
            .pipe(
                filter((res: HttpResponse<ILessonHistoryPreparation[]>) => res.ok),
                map((res: HttpResponse<ILessonHistoryPreparation[]>) => res.body)
            )
            .subscribe(
                (res: ILessonHistoryPreparation[]) => {
                    this.lessonHistoryPreparations = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLessonHistoryPreparations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILessonHistoryPreparation) {
        return item.id;
    }

    registerChangeInLessonHistoryPreparations() {
        this.eventSubscriber = this.eventManager.subscribe('lessonHistoryPreparationListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
