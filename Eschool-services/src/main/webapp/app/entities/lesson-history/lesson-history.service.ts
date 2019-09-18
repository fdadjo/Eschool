import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILessonHistory } from 'app/shared/model/lesson-history.model';

type EntityResponseType = HttpResponse<ILessonHistory>;
type EntityArrayResponseType = HttpResponse<ILessonHistory[]>;

@Injectable({ providedIn: 'root' })
export class LessonHistoryService {
    public resourceUrl = SERVER_API_URL + 'api/lesson-histories';

    constructor(protected http: HttpClient) {}

    create(lessonHistory: ILessonHistory): Observable<EntityResponseType> {
        return this.http.post<ILessonHistory>(this.resourceUrl, lessonHistory, { observe: 'response' });
    }

    update(lessonHistory: ILessonHistory): Observable<EntityResponseType> {
        return this.http.put<ILessonHistory>(this.resourceUrl, lessonHistory, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILessonHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILessonHistory[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
