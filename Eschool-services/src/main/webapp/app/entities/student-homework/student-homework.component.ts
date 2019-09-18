import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IStudentHomework } from 'app/shared/model/student-homework.model';
import { AccountService } from 'app/core';
import { StudentHomeworkService } from './student-homework.service';

@Component({
    selector: 'jhi-student-homework',
    templateUrl: './student-homework.component.html'
})
export class StudentHomeworkComponent implements OnInit, OnDestroy {
    studentHomeworks: IStudentHomework[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected studentHomeworkService: StudentHomeworkService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.studentHomeworkService
            .query()
            .pipe(
                filter((res: HttpResponse<IStudentHomework[]>) => res.ok),
                map((res: HttpResponse<IStudentHomework[]>) => res.body)
            )
            .subscribe(
                (res: IStudentHomework[]) => {
                    this.studentHomeworks = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInStudentHomeworks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IStudentHomework) {
        return item.id;
    }

    registerChangeInStudentHomeworks() {
        this.eventSubscriber = this.eventManager.subscribe('studentHomeworkListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
