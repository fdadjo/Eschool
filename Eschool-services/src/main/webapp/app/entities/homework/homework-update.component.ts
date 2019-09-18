import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IHomework } from 'app/shared/model/homework.model';
import { HomeworkService } from './homework.service';

@Component({
    selector: 'jhi-homework-update',
    templateUrl: './homework-update.component.html'
})
export class HomeworkUpdateComponent implements OnInit {
    homework: IHomework;
    isSaving: boolean;

    constructor(protected homeworkService: HomeworkService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ homework }) => {
            this.homework = homework;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.homework.id !== undefined) {
            this.subscribeToSaveResponse(this.homeworkService.update(this.homework));
        } else {
            this.subscribeToSaveResponse(this.homeworkService.create(this.homework));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IHomework>>) {
        result.subscribe((res: HttpResponse<IHomework>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
