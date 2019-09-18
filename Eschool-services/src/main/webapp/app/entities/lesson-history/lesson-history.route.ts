import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LessonHistory } from 'app/shared/model/lesson-history.model';
import { LessonHistoryService } from './lesson-history.service';
import { LessonHistoryComponent } from './lesson-history.component';
import { LessonHistoryDetailComponent } from './lesson-history-detail.component';
import { LessonHistoryUpdateComponent } from './lesson-history-update.component';
import { LessonHistoryDeletePopupComponent } from './lesson-history-delete-dialog.component';
import { ILessonHistory } from 'app/shared/model/lesson-history.model';

@Injectable({ providedIn: 'root' })
export class LessonHistoryResolve implements Resolve<ILessonHistory> {
    constructor(private service: LessonHistoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILessonHistory> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<LessonHistory>) => response.ok),
                map((lessonHistory: HttpResponse<LessonHistory>) => lessonHistory.body)
            );
        }
        return of(new LessonHistory());
    }
}

export const lessonHistoryRoute: Routes = [
    {
        path: '',
        component: LessonHistoryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.lessonHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LessonHistoryDetailComponent,
        resolve: {
            lessonHistory: LessonHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.lessonHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LessonHistoryUpdateComponent,
        resolve: {
            lessonHistory: LessonHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.lessonHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LessonHistoryUpdateComponent,
        resolve: {
            lessonHistory: LessonHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.lessonHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lessonHistoryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: LessonHistoryDeletePopupComponent,
        resolve: {
            lessonHistory: LessonHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.lessonHistory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
