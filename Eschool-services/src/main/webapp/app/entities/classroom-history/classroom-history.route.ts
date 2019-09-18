import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ClassroomHistory } from 'app/shared/model/classroom-history.model';
import { ClassroomHistoryService } from './classroom-history.service';
import { ClassroomHistoryComponent } from './classroom-history.component';
import { ClassroomHistoryDetailComponent } from './classroom-history-detail.component';
import { ClassroomHistoryUpdateComponent } from './classroom-history-update.component';
import { ClassroomHistoryDeletePopupComponent } from './classroom-history-delete-dialog.component';
import { IClassroomHistory } from 'app/shared/model/classroom-history.model';

@Injectable({ providedIn: 'root' })
export class ClassroomHistoryResolve implements Resolve<IClassroomHistory> {
    constructor(private service: ClassroomHistoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IClassroomHistory> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ClassroomHistory>) => response.ok),
                map((classroomHistory: HttpResponse<ClassroomHistory>) => classroomHistory.body)
            );
        }
        return of(new ClassroomHistory());
    }
}

export const classroomHistoryRoute: Routes = [
    {
        path: '',
        component: ClassroomHistoryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.classroomHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ClassroomHistoryDetailComponent,
        resolve: {
            classroomHistory: ClassroomHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.classroomHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ClassroomHistoryUpdateComponent,
        resolve: {
            classroomHistory: ClassroomHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.classroomHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ClassroomHistoryUpdateComponent,
        resolve: {
            classroomHistory: ClassroomHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.classroomHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const classroomHistoryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ClassroomHistoryDeletePopupComponent,
        resolve: {
            classroomHistory: ClassroomHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.classroomHistory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
