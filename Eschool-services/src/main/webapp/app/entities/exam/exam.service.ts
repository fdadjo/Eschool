import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExam } from 'app/shared/model/exam.model';

type EntityResponseType = HttpResponse<IExam>;
type EntityArrayResponseType = HttpResponse<IExam[]>;

@Injectable({ providedIn: 'root' })
export class ExamService {
    public resourceUrl = SERVER_API_URL + 'api/exams';

    constructor(protected http: HttpClient) {}

    create(exam: IExam): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(exam);
        return this.http
            .post<IExam>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(exam: IExam): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(exam);
        return this.http
            .put<IExam>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IExam>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IExam[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(exam: IExam): IExam {
        const copy: IExam = Object.assign({}, exam, {
            plannedOn: exam.plannedOn != null && exam.plannedOn.isValid() ? exam.plannedOn.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.plannedOn = res.body.plannedOn != null ? moment(res.body.plannedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((exam: IExam) => {
                exam.plannedOn = exam.plannedOn != null ? moment(exam.plannedOn) : null;
            });
        }
        return res;
    }
}
