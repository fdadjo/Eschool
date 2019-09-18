import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SchoolAuthority } from 'app/shared/model/school-authority.model';
import { SchoolAuthorityService } from './school-authority.service';
import { SchoolAuthorityComponent } from './school-authority.component';
import { SchoolAuthorityDetailComponent } from './school-authority-detail.component';
import { SchoolAuthorityUpdateComponent } from './school-authority-update.component';
import { SchoolAuthorityDeletePopupComponent } from './school-authority-delete-dialog.component';
import { ISchoolAuthority } from 'app/shared/model/school-authority.model';

@Injectable({ providedIn: 'root' })
export class SchoolAuthorityResolve implements Resolve<ISchoolAuthority> {
    constructor(private service: SchoolAuthorityService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISchoolAuthority> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SchoolAuthority>) => response.ok),
                map((schoolAuthority: HttpResponse<SchoolAuthority>) => schoolAuthority.body)
            );
        }
        return of(new SchoolAuthority());
    }
}

export const schoolAuthorityRoute: Routes = [
    {
        path: '',
        component: SchoolAuthorityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.schoolAuthority.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SchoolAuthorityDetailComponent,
        resolve: {
            schoolAuthority: SchoolAuthorityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.schoolAuthority.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SchoolAuthorityUpdateComponent,
        resolve: {
            schoolAuthority: SchoolAuthorityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.schoolAuthority.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SchoolAuthorityUpdateComponent,
        resolve: {
            schoolAuthority: SchoolAuthorityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.schoolAuthority.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const schoolAuthorityPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SchoolAuthorityDeletePopupComponent,
        resolve: {
            schoolAuthority: SchoolAuthorityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.schoolAuthority.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
