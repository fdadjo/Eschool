import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Abscence } from 'app/shared/model/abscence.model';
import { AbscenceService } from './abscence.service';
import { AbscenceComponent } from './abscence.component';
import { AbscenceDetailComponent } from './abscence-detail.component';
import { AbscenceUpdateComponent } from './abscence-update.component';
import { AbscenceDeletePopupComponent } from './abscence-delete-dialog.component';
import { IAbscence } from 'app/shared/model/abscence.model';

@Injectable({ providedIn: 'root' })
export class AbscenceResolve implements Resolve<IAbscence> {
    constructor(private service: AbscenceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAbscence> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Abscence>) => response.ok),
                map((abscence: HttpResponse<Abscence>) => abscence.body)
            );
        }
        return of(new Abscence());
    }
}

export const abscenceRoute: Routes = [
    {
        path: '',
        component: AbscenceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.abscence.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AbscenceDetailComponent,
        resolve: {
            abscence: AbscenceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.abscence.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AbscenceUpdateComponent,
        resolve: {
            abscence: AbscenceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.abscence.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AbscenceUpdateComponent,
        resolve: {
            abscence: AbscenceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.abscence.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const abscencePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AbscenceDeletePopupComponent,
        resolve: {
            abscence: AbscenceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'eschoolApp.abscence.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
