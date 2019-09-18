import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Homework } from 'app/shared/model/homework.model';
import { HomeworkService } from './homework.service';
import { HomeworkComponent } from './homework.component';
import { HomeworkDetailComponent } from './homework-detail.component';
import { HomeworkUpdateComponent } from './homework-update.component';
import { HomeworkDeletePopupComponent } from './homework-delete-dialog.component';
import { IHomework } from 'app/shared/model/homework.model';

@Injectable({ providedIn: 'root' })
export class HomeworkResolve implements Resolve<IHomework> {
    constructor(private service: HomeworkService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IHomework> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Homework>) => response.ok),
                map((homework: HttpResponse<Homework>) => homework.body)
            );
        }
        return of(new Homework());
    }
}

export const homeworkRoute: Routes = [
    {
        path: '',
        component: HomeworkComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.homework.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: HomeworkDetailComponent,
        resolve: {
            homework: HomeworkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.homework.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: HomeworkUpdateComponent,
        resolve: {
            homework: HomeworkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.homework.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: HomeworkUpdateComponent,
        resolve: {
            homework: HomeworkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.homework.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const homeworkPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: HomeworkDeletePopupComponent,
        resolve: {
            homework: HomeworkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.homework.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
