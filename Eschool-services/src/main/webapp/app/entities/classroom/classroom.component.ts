import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IClassroom } from 'app/shared/model/classroom.model';
import { AccountService } from 'app/core';
import { ClassroomService } from './classroom.service';

@Component({
    selector: 'jhi-classroom',
    templateUrl: './classroom.component.html'
})
export class ClassroomComponent implements OnInit, OnDestroy {
    classrooms: IClassroom[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected classroomService: ClassroomService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.classroomService
            .query()
            .pipe(
                filter((res: HttpResponse<IClassroom[]>) => res.ok),
                map((res: HttpResponse<IClassroom[]>) => res.body)
            )
            .subscribe(
                (res: IClassroom[]) => {
                    this.classrooms = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInClassrooms();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IClassroom) {
        return item.id;
    }

    registerChangeInClassrooms() {
        this.eventSubscriber = this.eventManager.subscribe('classroomListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
