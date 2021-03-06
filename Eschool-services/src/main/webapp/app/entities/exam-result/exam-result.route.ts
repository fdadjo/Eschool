import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ExamResult } from 'app/shared/model/exam-result.model';
import { ExamResultService } from './exam-result.service';
import { ExamResultComponent } from './exam-result.component';
import { ExamResultDetailComponent } from './exam-result-detail.component';
import { ExamResultUpdateComponent } from './exam-result-update.component';
import { ExamResultDeletePopupComponent } from './exam-result-delete-dialog.component';
import { IExamResult } from 'app/shared/model/exam-result.model';

@Injectable({ providedIn: 'root' })
export class ExamResultResolve implements Resolve<IExamResult> {
    constructor(private service: ExamResultService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IExamResult> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ExamResult>) => response.ok),
                map((examResult: HttpResponse<ExamResult>) => examResult.body)
            );
        }
        return of(new ExamResult());
    }
}

export const examResultRoute: Routes = [
    {
        path: '',
        component: ExamResultComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.examResult.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ExamResultDetailComponent,
        resolve: {
            examResult: ExamResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.examResult.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ExamResultUpdateComponent,
        resolve: {
            examResult: ExamResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.examResult.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ExamResultUpdateComponent,
        resolve: {
            examResult: ExamResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.examResult.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const examResultPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ExamResultDeletePopupComponent,
        resolve: {
            examResult: ExamResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.examResult.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
