import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISchoolAuthority } from 'app/shared/model/school-authority.model';

type EntityResponseType = HttpResponse<ISchoolAuthority>;
type EntityArrayResponseType = HttpResponse<ISchoolAuthority[]>;

@Injectable({ providedIn: 'root' })
export class SchoolAuthorityService {
    public resourceUrl = SERVER_API_URL + 'api/school-authorities';

    constructor(protected http: HttpClient) {}

    create(schoolAuthority: ISchoolAuthority): Observable<EntityResponseType> {
        return this.http.post<ISchoolAuthority>(this.resourceUrl, schoolAuthority, { observe: 'response' });
    }

    update(schoolAuthority: ISchoolAuthority): Observable<EntityResponseType> {
        return this.http.put<ISchoolAuthority>(this.resourceUrl, schoolAuthority, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISchoolAuthority>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISchoolAuthority[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
