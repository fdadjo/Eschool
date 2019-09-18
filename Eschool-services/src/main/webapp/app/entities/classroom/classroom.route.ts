import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Classroom } from 'app/shared/model/classroom.model';
import { ClassroomService } from './classroom.service';
import { ClassroomComponent } from './classroom.component';
import { ClassroomDetailComponent } from './classroom-detail.component';
import { ClassroomUpdateComponent } from './classroom-update.component';
import { ClassroomDeletePopupComponent } from './classroom-delete-dialog.component';
import { IClassroom } from 'app/shared/model/classroom.model';

@Injectable({ providedIn: 'root' })
export class ClassroomResolve implements Resolve<IClassroom> {
    constructor(private service: ClassroomService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IClassroom> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Classroom>) => response.ok),
                map((classroom: HttpResponse<Classroom>) => classroom.body)
            );
        }
        return of(new Classroom());
    }
}

export const classroomRoute: Routes = [
    {
        path: '',
        component: ClassroomComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.classroom.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ClassroomDetailComponent,
        resolve: {
            classroom: ClassroomResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.classroom.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ClassroomUpdateComponent,
        resolve: {
            classroom: ClassroomResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.classroom.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ClassroomUpdateComponent,
        resolve: {
            classroom: ClassroomResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.classroom.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const classroomPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ClassroomDeletePopupComponent,
        resolve: {
            classroom: ClassroomResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.classroom.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
