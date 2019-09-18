import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISchoolAuthority } from 'app/shared/model/school-authority.model';
import { SchoolAuthorityService } from './school-authority.service';
import { ISchool } from 'app/shared/model/school.model';
import { SchoolService } from 'app/entities/school';
import { IUser, UserService } from 'app/core';
import { IAuthority } from 'app/shared/model/authority.model';
import { AuthorityService } from 'app/entities/authority';

@Component({
    selector: 'jhi-school-authority-update',
    templateUrl: './school-authority-update.component.html'
})
export class SchoolAuthorityUpdateComponent implements OnInit {
    schoolAuthority: ISchoolAuthority;
    isSaving: boolean;

    schools: ISchool[];

    users: IUser[];

    authorities: IAuthority[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected schoolAuthorityService: SchoolAuthorityService,
        protected schoolService: SchoolService,
        protected userService: UserService,
        protected authorityService: AuthorityService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ schoolAuthority }) => {
            this.schoolAuthority = schoolAuthority;
        });
        this.schoolService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISchool[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISchool[]>) => response.body)
            )
            .subscribe((res: ISchool[]) => (this.schools = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.authorityService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAuthority[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAuthority[]>) => response.body)
            )
            .subscribe((res: IAuthority[]) => (this.authorities = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.schoolAuthority.id !== undefined) {
            this.subscribeToSaveResponse(this.schoolAuthorityService.update(this.schoolAuthority));
        } else {
            this.subscribeToSaveResponse(this.schoolAuthorityService.create(this.schoolAuthority));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchoolAuthority>>) {
        result.subscribe((res: HttpResponse<ISchoolAuthority>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackSchoolById(index: number, item: ISchool) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackAuthorityById(index: number, item: IAuthority) {
        return item.id;
    }
}
