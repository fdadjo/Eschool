import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISchool } from 'app/shared/model/school.model';
import { AccountService } from 'app/core';
import { SchoolService } from './school.service';

@Component({
    selector: 'jhi-school',
    templateUrl: './school.component.html'
})
export class SchoolComponent implements OnInit, OnDestroy {
    schools: ISchool[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected schoolService: SchoolService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.schoolService
            .query()
            .pipe(
                filter((res: HttpResponse<ISchool[]>) => res.ok),
                map((res: HttpResponse<ISchool[]>) => res.body)
            )
            .subscribe(
                (res: ISchool[]) => {
                    this.schools = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSchools();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISchool) {
        return item.id;
    }

    registerChangeInSchools() {
        this.eventSubscriber = this.eventManager.subscribe('schoolListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
