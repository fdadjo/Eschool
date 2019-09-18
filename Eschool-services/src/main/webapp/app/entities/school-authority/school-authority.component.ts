import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISchoolAuthority } from 'app/shared/model/school-authority.model';
import { AccountService } from 'app/core';
import { SchoolAuthorityService } from './school-authority.service';

@Component({
    selector: 'jhi-school-authority',
    templateUrl: './school-authority.component.html'
})
export class SchoolAuthorityComponent implements OnInit, OnDestroy {
    schoolAuthorities: ISchoolAuthority[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected schoolAuthorityService: SchoolAuthorityService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.schoolAuthorityService
            .query()
            .pipe(
                filter((res: HttpResponse<ISchoolAuthority[]>) => res.ok),
                map((res: HttpResponse<ISchoolAuthority[]>) => res.body)
            )
            .subscribe(
                (res: ISchoolAuthority[]) => {
                    this.schoolAuthorities = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSchoolAuthorities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISchoolAuthority) {
        return item.id;
    }

    registerChangeInSchoolAuthorities() {
        this.eventSubscriber = this.eventManager.subscribe('schoolAuthorityListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
