import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IClassroom } from 'app/shared/model/classroom.model';
import { ClassroomService } from './classroom.service';

@Component({
    selector: 'jhi-classroom-update',
    templateUrl: './classroom-update.component.html'
})
export class ClassroomUpdateComponent implements OnInit {
    classroom: IClassroom;
    isSaving: boolean;

    constructor(protected classroomService: ClassroomService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ classroom }) => {
            this.classroom = classroom;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.classroom.id !== undefined) {
            this.subscribeToSaveResponse(this.classroomService.update(this.classroom));
        } else {
            this.subscribeToSaveResponse(this.classroomService.create(this.classroom));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IClassroom>>) {
        result.subscribe((res: HttpResponse<IClassroom>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
