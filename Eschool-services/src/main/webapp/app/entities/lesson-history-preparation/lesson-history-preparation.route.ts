import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LessonHistoryPreparation } from 'app/shared/model/lesson-history-preparation.model';
import { LessonHistoryPreparationService } from './lesson-history-preparation.service';
import { LessonHistoryPreparationComponent } from './lesson-history-preparation.component';
import { LessonHistoryPreparationDetailComponent } from './lesson-history-preparation-detail.component';
import { LessonHistoryPreparationUpdateComponent } from './lesson-history-preparation-update.component';
import { LessonHistoryPreparationDeletePopupComponent } from './lesson-history-preparation-delete-dialog.component';
import { ILessonHistoryPreparation } from 'app/shared/model/lesson-history-preparation.model';

@Injectable({ providedIn: 'root' })
export class LessonHistoryPreparationResolve implements Resolve<ILessonHistoryPreparation> {
    constructor(private service: LessonHistoryPreparationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILessonHistoryPreparation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<LessonHistoryPreparation>) => response.ok),
                map((lessonHistoryPreparation: HttpResponse<LessonHistoryPreparation>) => lessonHistoryPreparation.body)
            );
        }
        return of(new LessonHistoryPreparation());
    }
}

export const lessonHistoryPreparationRoute: Routes = [
    {
        path: '',
        component: LessonHistoryPreparationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.lessonHistoryPreparation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LessonHistoryPreparationDetailComponent,
        resolve: {
            lessonHistoryPreparation: LessonHistoryPreparationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.lessonHistoryPreparation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LessonHistoryPreparationUpdateComponent,
        resolve: {
            lessonHistoryPreparation: LessonHistoryPreparationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.lessonHistoryPreparation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LessonHistoryPreparationUpdateComponent,
        resolve: {
            lessonHistoryPreparation: LessonHistoryPreparationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.lessonHistoryPreparation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lessonHistoryPreparationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: LessonHistoryPreparationDeletePopupComponent,
        resolve: {
            lessonHistoryPreparation: LessonHistoryPreparationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.lessonHistoryPreparation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
