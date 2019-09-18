import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {Response} from "@angular/http";
import {Observer, Observable} from "rxjs";
import {HttpInterceptorService} from "./http-interceptor.service";
import {Student} from '../models/Student';
import {Absence} from "../models/Absence";
import {School} from "../models/School";
import {User} from "../models/User";

interface Storage  {
  absences:  Absence[];
  schools:  School[];
  students: User[];
}

@Injectable()
export class AbsenceService {

  absences$: Observable<any>;
  private _absencesObserver: Observer<any>;

  schools$: Observable<any>;
  private _schoolsObserver: Observer<any>;

  students$: Observable<any>;
  private _studentsObserver: Observer<any>;
  
  private storage : Storage = <Storage>{};

  private handleError (error: any) {
    let errMsg = (error.message) ? error.message : error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    return Promise.reject(errMsg);
  }

  private extractData(res: Response) {
    let body;
    if (res.text()) { body = res.json() }
    return body;
  }

  constructor(private _http : HttpInterceptorService) {

    this.absences$ = new Observable<any>(observer => {
      this._absencesObserver = observer;
    }).share();

    this.schools$ = new Observable<any>(observer => {
      this._schoolsObserver = observer;
    }).share();

    this.students$ = new Observable<any>(observer => {
      this._studentsObserver = observer;
    }).share();

    this.students$ = new Observable<User>(observer => {
      this._studentsObserver = observer;
    }).share();
  }

  getFullUserAbsences(idUser) {

    return this._http.get(environment.URL + "/absences/user/" + idUser).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  // Function use To get class accounting
  getFullClassAbsences(idClass) {

    return this._http.get(environment.URL + "/absences/all/class/" + idClass).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  // Function use To get absence by Id
  getAbsenceById(id) {
    return this._http.get(environment.URL + "/absences/" + id).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  // Function use to update payment
  update(data) {
    return this._http.put(environment.URL + "/absences/", data).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  getAbsencesForUserId(idUser, page) {

    this._http.get(environment.URL + "/absences/" + idUser + '?page=' + page).subscribe(
      /*(res: HttpResponse<any>) => {
        this.storage.absences = res.body;
        this._absencesObserver.next(this.storage.absences);
      }*/
      (res : Response) => {
        this.storage.absences = res.json();
        this._absencesObserver.next(this.storage.absences);
      }
    );
  }

  // Function use to create a new absence
  create(data) {
    return this._http.post(environment.URL + "/absences/", data).toPromise()
      /*.then((success: HttpResponse<any>) => {
          let body;
          if (success.statusText) {
            body = success.body;
          }
          return body;
        },
        error => console.log(error))
      .catch(this.handleError);*/
  }


}

