import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IHomework } from 'app/shared/model/homework.model';
import { AccountService } from 'app/core';
import { HomeworkService } from './homework.service';

@Component({
    selector: 'jhi-homework',
    templateUrl: './homework.component.html'
})
export class HomeworkComponent implements OnInit, OnDestroy {
    homework: IHomework[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected homeworkService: HomeworkService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.homeworkService
            .query()
            .pipe(
                filter((res: HttpResponse<IHomework[]>) => res.ok),
                map((res: HttpResponse<IHomework[]>) => res.body)
            )
            .subscribe(
                (res: IHomework[]) => {
                    this.homework = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInHomework();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IHomework) {
        return item.id;
    }

    registerChangeInHomework() {
        this.eventSubscriber = this.eventManager.subscribe('homeworkListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
