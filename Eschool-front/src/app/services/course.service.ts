
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Observer} from 'rxjs/Observer';
import {environment} from '../../environments/environment';
import {Subject} from 'rxjs/Subject';
import {Course} from '../models/Course';
import {HttpClient, HttpResponse} from '@angular/common/http';

interface Storage  {
  course: [any];
  lessonHstryPrep: [any];
}

@Injectable()
export class CourseService {

  course$: Observable<any>;
  private _courseObserver: Observer<any>;

  category$: Observable<any>;
  private _categoryObserver: Observer<any>;

  lessonHstryPrep$: Observable<any>;
  private _lessonHstryPrepObserver: Observer<any>;

  private storage: Storage = <Storage>{};

  private lessonHistory_url = environment.URL + '/lessonHistory';

  // ---- When a new course is created, all the component which have a course box will be updated
  private courseSubject: Subject<Course> = new Subject<Course>();

  setCourseCreated(courseCreated: Course): void {
    this.courseSubject.next(courseCreated);
  }

  getCourseCreated(): Observable<Course> {
    return this.courseSubject.asObservable();
  }
  // ---- End

  private handleError (error: any) {
    const errMsg = (error.message) ? error.message : error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    return Promise.reject(errMsg);
  }

  private extractData(res: HttpResponse<Object>) {
    let body;
    if (res) {
      body = res.body;
    }
    return body;
  }

  constructor(private _http: HttpClient) {

    this.course$ = new Observable<Course>(observer => {
      this._courseObserver = observer;
    }).share();

    this.category$ = new Observable<any>(observer => {
      this._categoryObserver = observer;
    }).share();

    this.lessonHstryPrep$ = new Observable<any>(observer => {
      this._lessonHstryPrepObserver = observer;
    }).share();
  }


  getAllLessonHstryPrep(schoolId, page) {

    return this._http.get(environment.URL + '/lessonHistoryPreparation/all/school/' + schoolId + '?page=' + page, { observe: 'response', responseType: 'json' }).subscribe(
      (res: HttpResponse<any>) => {
        this.storage.lessonHstryPrep = res.body;
        this._lessonHstryPrepObserver.next(this.storage.lessonHstryPrep);
      }
    );
  }

  createLessonHstryPrep(data) {
    return this._http.post(environment.URL + '/lessonHistoryPreparation/create', data, { observe: 'response', responseType: 'json' }).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  deleteLessonHstryPrep(id) {
    return this._http.delete(environment.URL + '/lessonHistoryPreparation/delete/' + id, { observe: 'response', responseType: 'json' }).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  // Function use To get All Courses
  getAllCourses(classId) {

    return this._http.get(this.lessonHistory_url + '/all/class/' + classId, { observe: 'response', responseType: 'json' }).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  // Function use To get All Courses
  getAllCoursesByClass(classId) {

    this._http.get(this.lessonHistory_url + '/all/class/' + classId, { observe: 'response', responseType: 'json' }).subscribe(
      (res: HttpResponse<any>) => {
        this.storage.course = res.body;
        this._courseObserver.next(this.storage.course);
      }
    );
  }

  // Function use To get All Courses
  getCoursesByClassPerPage(classId, page) {

    this._http.get(this.lessonHistory_url + '/page/class/' + classId + '?page=' + page, { observe: 'response', responseType: 'json' }).subscribe(
      (res: HttpResponse<any>) => {
        this.storage.course = res.body;
        this._courseObserver.next(this.storage.course);
      }
    );
  }

  // Function use To get Course by id
  getCourseById(id) {
    return this._http.get(this.lessonHistory_url + '/' + id, { observe: 'response', responseType: 'json' }).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  // Function use to Create
  createCourse(data) {
    return this._http.post(this.lessonHistory_url + '/create', data, { observe: 'response', responseType: 'json' }).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  // Function use to update
  updateCourse(data) {
    return this._http.put(this.lessonHistory_url + '/update', data, { observe: 'response', responseType: 'json' }).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }
}

