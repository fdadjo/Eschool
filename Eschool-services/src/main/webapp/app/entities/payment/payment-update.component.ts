import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPayment } from 'app/shared/model/payment.model';
import { PaymentService } from './payment.service';
import { IUser, UserService } from 'app/core';
import { IClassroom } from 'app/shared/model/classroom.model';
import { ClassroomService } from 'app/entities/classroom';
import { ISchool } from 'app/shared/model/school.model';
import { SchoolService } from 'app/entities/school';

@Component({
    selector: 'jhi-payment-update',
    templateUrl: './payment-update.component.html'
})
export class PaymentUpdateComponent implements OnInit {
    payment: IPayment;
    isSaving: boolean;

    users: IUser[];

    classrooms: IClassroom[];

    schools: ISchool[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected paymentService: PaymentService,
        protected userService: UserService,
        protected classroomService: ClassroomService,
        protected schoolService: SchoolService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ payment }) => {
            this.payment = payment;
        });
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.classroomService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IClassroom[]>) => mayBeOk.ok),
                map((response: HttpResponse<IClassroom[]>) => response.body)
            )
            .subscribe((res: IClassroom[]) => (this.classrooms = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.schoolService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISchool[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISchool[]>) => response.body)
            )
            .subscribe((res: ISchool[]) => (this.schools = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.payment.id !== undefined) {
            this.subscribeToSaveResponse(this.paymentService.update(this.payment));
        } else {
            this.subscribeToSaveResponse(this.paymentService.create(this.payment));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPayment>>) {
        result.subscribe((res: HttpResponse<IPayment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackClassroomById(index: number, item: IClassroom) {
        return item.id;
    }

    trackSchoolById(index: number, item: ISchool) {
        return item.id;
    }
}
