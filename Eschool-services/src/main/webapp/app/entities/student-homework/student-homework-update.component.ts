import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IStudentHomework } from 'app/shared/model/student-homework.model';
import { StudentHomeworkService } from './student-homework.service';
import { IUser, UserService } from 'app/core';
import { IHomework } from 'app/shared/model/homework.model';
import { HomeworkService } from 'app/entities/homework';

@Component({
    selector: 'jhi-student-homework-update',
    templateUrl: './student-homework-update.component.html'
})
export class StudentHomeworkUpdateComponent implements OnInit {
    studentHomework: IStudentHomework;
    isSaving: boolean;

    users: IUser[];

    homework: IHomework[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected studentHomeworkService: StudentHomeworkService,
        protected userService: UserService,
        protected homeworkService: HomeworkService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ studentHomework }) => {
            this.studentHomework = studentHomework;
        });
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.homeworkService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IHomework[]>) => mayBeOk.ok),
                map((response: HttpResponse<IHomework[]>) => response.body)
            )
            .subscribe((res: IHomework[]) => (this.homework = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.studentHomework.id !== undefined) {
            this.subscribeToSaveResponse(this.studentHomeworkService.update(this.studentHomework));
        } else {
            this.subscribeToSaveResponse(this.studentHomeworkService.create(this.studentHomework));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudentHomework>>) {
        result.subscribe((res: HttpResponse<IStudentHomework>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackHomeworkById(index: number, item: IHomework) {
        return item.id;
    }
}
