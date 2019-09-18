import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IAbscence } from 'app/shared/model/abscence.model';
import { AbscenceService } from './abscence.service';

@Component({
    selector: 'jhi-abscence-update',
    templateUrl: './abscence-update.component.html'
})
export class AbscenceUpdateComponent implements OnInit {
    abscence: IAbscence;
    isSaving: boolean;
    date: string;

    constructor(protected abscenceService: AbscenceService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ abscence }) => {
            this.abscence = abscence;
            this.date = this.abscence.date != null ? this.abscence.date.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.abscence.date = this.date != null ? moment(this.date, DATE_TIME_FORMAT) : null;
        if (this.abscence.id !== undefined) {
            this.subscribeToSaveResponse(this.abscenceService.update(this.abscence));
        } else {
            this.subscribeToSaveResponse(this.abscenceService.create(this.abscence));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAbscence>>) {
        result.subscribe((res: HttpResponse<IAbscence>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
