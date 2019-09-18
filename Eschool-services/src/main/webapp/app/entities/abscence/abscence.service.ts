import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAbscence } from 'app/shared/model/abscence.model';

type EntityResponseType = HttpResponse<IAbscence>;
type EntityArrayResponseType = HttpResponse<IAbscence[]>;

@Injectable({ providedIn: 'root' })
export class AbscenceService {
    public resourceUrl = SERVER_API_URL + 'api/abscences';

    constructor(protected http: HttpClient) {}

    create(abscence: IAbscence): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(abscence);
        return this.http
            .post<IAbscence>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(abscence: IAbscence): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(abscence);
        return this.http
            .put<IAbscence>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAbscence>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAbscence[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(abscence: IAbscence): IAbscence {
        const copy: IAbscence = Object.assign({}, abscence, {
            date: abscence.date != null && abscence.date.isValid() ? abscence.date.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.date = res.body.date != null ? moment(res.body.date) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((abscence: IAbscence) => {
                abscence.date = abscence.date != null ? moment(abscence.date) : null;
            });
        }
        return res;
    }
}
