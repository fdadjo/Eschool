import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILessonHistoryPreparation } from 'app/shared/model/lesson-history-preparation.model';

type EntityResponseType = HttpResponse<ILessonHistoryPreparation>;
type EntityArrayResponseType = HttpResponse<ILessonHistoryPreparation[]>;

@Injectable({ providedIn: 'root' })
export class LessonHistoryPreparationService {
    public resourceUrl = SERVER_API_URL + 'api/lesson-history-preparations';

    constructor(protected http: HttpClient) {}

    create(lessonHistoryPreparation: ILessonHistoryPreparation): Observable<EntityResponseType> {
        return this.http.post<ILessonHistoryPreparation>(this.resourceUrl, lessonHistoryPreparation, { observe: 'response' });
    }

    update(lessonHistoryPreparation: ILessonHistoryPreparation): Observable<EntityResponseType> {
        return this.http.put<ILessonHistoryPreparation>(this.resourceUrl, lessonHistoryPreparation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILessonHistoryPreparation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILessonHistoryPreparation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
