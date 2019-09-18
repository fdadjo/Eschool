import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITimeSlotHistory } from 'app/shared/model/time-slot-history.model';

type EntityResponseType = HttpResponse<ITimeSlotHistory>;
type EntityArrayResponseType = HttpResponse<ITimeSlotHistory[]>;

@Injectable({ providedIn: 'root' })
export class TimeSlotHistoryService {
    public resourceUrl = SERVER_API_URL + 'api/time-slot-histories';

    constructor(protected http: HttpClient) {}

    create(timeSlotHistory: ITimeSlotHistory): Observable<EntityResponseType> {
        return this.http.post<ITimeSlotHistory>(this.resourceUrl, timeSlotHistory, { observe: 'response' });
    }

    update(timeSlotHistory: ITimeSlotHistory): Observable<EntityResponseType> {
        return this.http.put<ITimeSlotHistory>(this.resourceUrl, timeSlotHistory, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITimeSlotHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITimeSlotHistory[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
