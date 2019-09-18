import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IClassroomHistory } from 'app/shared/model/classroom-history.model';

type EntityResponseType = HttpResponse<IClassroomHistory>;
type EntityArrayResponseType = HttpResponse<IClassroomHistory[]>;

@Injectable({ providedIn: 'root' })
export class ClassroomHistoryService {
    public resourceUrl = SERVER_API_URL + 'api/classroom-histories';

    constructor(protected http: HttpClient) {}

    create(classroomHistory: IClassroomHistory): Observable<EntityResponseType> {
        return this.http.post<IClassroomHistory>(this.resourceUrl, classroomHistory, { observe: 'response' });
    }

    update(classroomHistory: IClassroomHistory): Observable<EntityResponseType> {
        return this.http.put<IClassroomHistory>(this.resourceUrl, classroomHistory, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IClassroomHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IClassroomHistory[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
