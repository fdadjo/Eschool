import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IJobHistory } from 'app/shared/model/job-history.model';

type EntityResponseType = HttpResponse<IJobHistory>;
type EntityArrayResponseType = HttpResponse<IJobHistory[]>;

@Injectable({ providedIn: 'root' })
export class JobHistoryService {
    public resourceUrl = SERVER_API_URL + 'api/job-histories';

    constructor(protected http: HttpClient) {}

    create(jobHistory: IJobHistory): Observable<EntityResponseType> {
        return this.http.post<IJobHistory>(this.resourceUrl, jobHistory, { observe: 'response' });
    }

    update(jobHistory: IJobHistory): Observable<EntityResponseType> {
        return this.http.put<IJobHistory>(this.resourceUrl, jobHistory, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IJobHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IJobHistory[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
