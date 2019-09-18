import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Observer} from 'rxjs/Observer';
import {environment} from '../../environments/environment';
import {Subject} from 'rxjs/Subject';
import {School} from '../models/School';
import {BehaviorSubject } from 'rxjs/BehaviorSubject';
import {HttpClient, HttpResponse} from '@angular/common/http';

interface Storage  {
  classroom: [any];
  classroomCourse: [any];
  classStat: [any];
  examsResult: [any];
  students: [any];
  category: [any];
  lesson: [any];
  courses: [any];
}

@Injectable()
export class ClassroomService {

  public valueId = 0;
  public classroomId$ = new BehaviorSubject(this.valueId);
  public lessonId$ = new BehaviorSubject(this.valueId);

  students$: Observable<any>;
  private _studentsObserver: Observer<any>;

  classroom$: Observable<any>;
  private _classroomObserver: Observer<any>;

  category$: Observable<any>;
  private _categoryObserver: Observer<any>;

  classStat$: Observable<any>;
  private _classStatObserver: Observer<any>;

  examsResult$: Observable<any>;
  private _examsResultObserver: Observer<any>;

  lesson$: Observable<any>;
  private _lessonObserver: Observer<any>;

  courses$: Observable<any>;
  private _coursesObserver: Observer<any>;

  private storage: Storage = <Storage>{};

  private class_url = environment.URL + '/classrooms';

  // ---- When a new user is created, all the component which avec a users box will be updated
  private schoolSubject: Subject<School> = new Subject<School>();

  setSchoolCreated(schoolCreated: School): void {
    this.schoolSubject.next(schoolCreated);
  }

  getSchoolCreated(): Observable<School> {
    return this.schoolSubject.asObservable();
  }
  // ---- End

  private handleError (error: any) {
    const errMsg = (error.message) ? error.message : error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    return Promise.reject(errMsg);
  }

  private extractData(res: HttpResponse<Object>) {
    let body;
    if (res.statusText) {
      body = res.body;
    }
    return body;
  }

  constructor(private _http: HttpClient) {

    this.classroom$ = new Observable<any>(observer => {
      this._classroomObserver = observer;
    }).share();

    this.lesson$ = new Observable<any>(observer => {
      this._lessonObserver = observer;
    }).share();

    this.courses$ = new Observable<any>(observer => {
      this._coursesObserver = observer;
    }).share();

    this.students$ = new Observable<any>(observer => {
      this._studentsObserver = observer;
    }).share();

    this.classStat$ = new Observable<any>(observer => {
      this._classStatObserver = observer;
    }).share();

    this.examsResult$ = new Observable<any>(observer => {
      this._examsResultObserver = observer;
    }).share();

    this.category$ = new Observable<any>(observer => {
      this._categoryObserver = observer;
    }).share();

  }

  // Function use to create one classroom
  createClassroom(data) {
    return this._http.post(this.class_url, data, { observe: 'response', responseType: 'json' }).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  // Function use To get All Classroom
  getClassroomsBySchoolIdAndPage(idSchool, page) {
    this._http.get(this.class_url + '/page/' + idSchool + '?page=' + page, { observe: 'response', responseType: 'json' }).subscribe(
      (res: HttpResponse<any>) => {
        this.storage.classroom = res.body;
        this._classroomObserver.next(this.storage.classroom);
      }
    );
  }

  // Function use To get All Classroom
  getClassroomsForSchoolIdAndPage(idSchool, page) {
    return this._http.get(this.class_url + '/page/' + idSchool + '?page=' + page, { observe: 'response', responseType: 'json' }).toPromise()
      .then((success: HttpResponse<any>) => {
          let body;
          if (success.statusText) {
            body = success.body;
          }
          return body;
        },
        error => console.log(error))
      .catch(this.handleError);
  }

  getClassroomsForSchoolId(idSchool) {
    this._http.get(this.class_url + '/all/' + idSchool, { observe: 'response', responseType: 'json' }).subscribe(
      (res: HttpResponse<any>) => {
        this.storage.classroom = res.body;
        this._classroomObserver.next(this.storage.classroom);
      }
    );
  }

  getClassroomsForSchoolIdPromise(idSchool) {
    return this._http.get(this.class_url + '/all/' + idSchool, { observe: 'response', responseType: 'json' }).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  getClassroomsBySchoolId(idSchool) {
    return this._http.get(this.class_url + '/all/' + idSchool, { observe: 'response', responseType: 'json' }).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  getClassroomsByUserId(userId) {
    return this._http.get(this.class_url + '/student/' + userId, { observe: 'response', responseType: 'json' }).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  // Function use To get Classroom by id
  getClassroomById(classroomId) {

    return  this._http.get(this.class_url + '/' + classroomId, { observe: 'response', responseType: 'json' }).toPromise()
      .then(this.extractData)
      .catch(this.handleError
      );
  }

  getClassroomByHomeworkId(homeworkId) {
    return this._http.get(this.class_url + '/homework/' + homeworkId, { observe: 'response', responseType: 'json' }).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  // Function use to create one school
  updateClassroom(data) {
    return this._http.put(this.class_url, data, { observe: 'response', responseType: 'json' }).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

}
