import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAbscence } from 'app/shared/model/abscence.model';
import { AccountService } from 'app/core';
import { AbscenceService } from './abscence.service';

@Component({
    selector: 'jhi-abscence',
    templateUrl: './abscence.component.html'
})
export class AbscenceComponent implements OnInit, OnDestroy {
    abscences: IAbscence[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected abscenceService: AbscenceService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.abscenceService
            .query()
            .pipe(
                filter((res: HttpResponse<IAbscence[]>) => res.ok),
                map((res: HttpResponse<IAbscence[]>) => res.body)
            )
            .subscribe(
                (res: IAbscence[]) => {
                    this.abscences = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAbscences();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAbscence) {
        return item.id;
    }

    registerChangeInAbscences() {
        this.eventSubscriber = this.eventManager.subscribe('abscenceListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
