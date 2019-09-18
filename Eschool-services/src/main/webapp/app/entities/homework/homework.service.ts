import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHomework } from 'app/shared/model/homework.model';

type EntityResponseType = HttpResponse<IHomework>;
type EntityArrayResponseType = HttpResponse<IHomework[]>;

@Injectable({ providedIn: 'root' })
export class HomeworkService {
    public resourceUrl = SERVER_API_URL + 'api/homework';

    constructor(protected http: HttpClient) {}

    create(homework: IHomework): Observable<EntityResponseType> {
        return this.http.post<IHomework>(this.resourceUrl, homework, { observe: 'response' });
    }

    update(homework: IHomework): Observable<EntityResponseType> {
        return this.http.put<IHomework>(this.resourceUrl, homework, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IHomework>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IHomework[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
