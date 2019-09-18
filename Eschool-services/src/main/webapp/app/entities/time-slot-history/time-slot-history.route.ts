import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TimeSlotHistory } from 'app/shared/model/time-slot-history.model';
import { TimeSlotHistoryService } from './time-slot-history.service';
import { TimeSlotHistoryComponent } from './time-slot-history.component';
import { TimeSlotHistoryDetailComponent } from './time-slot-history-detail.component';
import { TimeSlotHistoryUpdateComponent } from './time-slot-history-update.component';
import { TimeSlotHistoryDeletePopupComponent } from './time-slot-history-delete-dialog.component';
import { ITimeSlotHistory } from 'app/shared/model/time-slot-history.model';

@Injectable({ providedIn: 'root' })
export class TimeSlotHistoryResolve implements Resolve<ITimeSlotHistory> {
    constructor(private service: TimeSlotHistoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITimeSlotHistory> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TimeSlotHistory>) => response.ok),
                map((timeSlotHistory: HttpResponse<TimeSlotHistory>) => timeSlotHistory.body)
            );
        }
        return of(new TimeSlotHistory());
    }
}

export const timeSlotHistoryRoute: Routes = [
    {
        path: '',
        component: TimeSlotHistoryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.timeSlotHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TimeSlotHistoryDetailComponent,
        resolve: {
            timeSlotHistory: TimeSlotHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.timeSlotHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TimeSlotHistoryUpdateComponent,
        resolve: {
            timeSlotHistory: TimeSlotHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.timeSlotHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TimeSlotHistoryUpdateComponent,
        resolve: {
            timeSlotHistory: TimeSlotHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.timeSlotHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const timeSlotHistoryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TimeSlotHistoryDeletePopupComponent,
        resolve: {
            timeSlotHistory: TimeSlotHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.timeSlotHistory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
