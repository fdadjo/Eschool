import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExamResult } from 'app/shared/model/exam-result.model';

type EntityResponseType = HttpResponse<IExamResult>;
type EntityArrayResponseType = HttpResponse<IExamResult[]>;

@Injectable({ providedIn: 'root' })
export class ExamResultService {
    public resourceUrl = SERVER_API_URL + 'api/exam-results';

    constructor(protected http: HttpClient) {}

    create(examResult: IExamResult): Observable<EntityResponseType> {
        return this.http.post<IExamResult>(this.resourceUrl, examResult, { observe: 'response' });
    }

    update(examResult: IExamResult): Observable<EntityResponseType> {
        return this.http.put<IExamResult>(this.resourceUrl, examResult, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IExamResult>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IExamResult[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
