import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStudentHomework } from 'app/shared/model/student-homework.model';

type EntityResponseType = HttpResponse<IStudentHomework>;
type EntityArrayResponseType = HttpResponse<IStudentHomework[]>;

@Injectable({ providedIn: 'root' })
export class StudentHomeworkService {
    public resourceUrl = SERVER_API_URL + 'api/student-homeworks';

    constructor(protected http: HttpClient) {}

    create(studentHomework: IStudentHomework): Observable<EntityResponseType> {
        return this.http.post<IStudentHomework>(this.resourceUrl, studentHomework, { observe: 'response' });
    }

    update(studentHomework: IStudentHomework): Observable<EntityResponseType> {
        return this.http.put<IStudentHomework>(this.resourceUrl, studentHomework, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IStudentHomework>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStudentHomework[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
