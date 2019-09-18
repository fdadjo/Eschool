import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Observer} from 'rxjs/Observer';
import {environment} from '../../environments/environment';
import {Student} from '../models/Student';
import {Subject} from 'rxjs/Subject';
import {Pageable} from '../models/Pageable';
import {HttpClient, HttpParams, HttpResponse} from '@angular/common/http';

interface Storage {
  students: [Student];
  student: Student;
}

@Injectable()
export class StudentService {

  students$: Observable<any>;
  private _studentsObserver: Observer<any>;

  student$: Observable<any>;
  private _studentObserver: Observer<any>;


  private storage: Storage = <Storage>{};

  private student_url = environment.URL + '/student';

  // ---- When a new student is created, all the component which have a student box will be updated
  private studentSubject: Subject<Student> = new Subject<Student>();

  setStudentCreated(studentCreated: Student): void {
    this.studentSubject.next(studentCreated);
  }

  getStudentCreated(): Observable<Student> {
    return this.studentSubject.asObservable();
  }
  // ---- End

  private handleError (error: any) {
    const errMsg = (error.message) ? error.message : error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    return Promise.reject(errMsg);
  }

  private extractData(res: HttpResponse<Object>) {
    let body;
    if (res) { body = res.body; }
    return body;
  }

  constructor(private _http: HttpClient) {

    this.students$ = new Observable<any>(observer => {
      this._studentsObserver = observer;
    }).share();

    this.student$ = new Observable<any>(observer => {
      this._studentObserver = observer;
    }).share();
  }

  // Function use To get All Students
  getAllStudents(id, page) {
    this._http.get(this.student_url + '/all/school/' + id + '?page=' + page,
      { observe: 'response', responseType: 'json' }).subscribe(
      (res: HttpResponse<any>) => {
        this.storage.students = res.body;
        this._studentsObserver.next(this.storage.students);
      }
    );
  }

  // Function use To get All Students
  getAllStudentsPromise(id, page) {
    return this._http.get(this.student_url + '/all/school/' + id + '?page=' + page,
      { observe: 'response', responseType: 'json' }).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }


  // Function use To get Student By Id
  getStudentById(id) {
    this._http.get(this.student_url + '/' + id, { observe: 'response', responseType: 'json' }).subscribe(
      (res: HttpResponse<any>) => {
        this.storage.student = res.body;
        this._studentObserver.next(this.storage.student);
      }
    );
  }

  // Function use To get Student By ClassroomId
  getStudentByClassroomId(id, pageable: Pageable) {

    const params = new HttpParams();
    params.append('page', pageable.page.toString());
    params.append('size', pageable.size.toString());

    return  this._http.get(this.student_url + '/classroomId/pageable/' + id, { params: params, observe: 'response', responseType: 'json' }).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  // Function use To get Student By ClassroomId
  getStudentByIdClassroom(id, pageable: Pageable) {

    const params = new HttpParams();
    params.append('page', pageable.page.toString());
    params.append('size', pageable.size.toString());

    this._http.get(this.student_url + '/classroomId/pageable/' + id, { params: params, observe: 'response', responseType: 'json' }).subscribe(
      (res: HttpResponse<any>) => {
        this.storage.student = res.body;
        this._studentsObserver.next(this.storage.student);
      }
    );
  }

  // Function use To get Student By ClassroomId
  getStudentByClassId(id) {

    return  this._http.get(this.student_url + '/classroomId/' + id, { observe: 'response', responseType: 'json' }).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  // Function use to create one student
  createStudent(id, data) {
    return this._http.post(this.student_url + '/register/school/' + id, data, { observe: 'response', responseType: 'json' }).toPromise()
      .then((success: HttpResponse<Object>) => {

          let body;
          if (success) {
            body = success.body;
            this.setStudentCreated(body);
          }
          return body;
        },
        error => console.log(error))
      .catch(this.handleError);
  }

  // Function use to update student
  updateStudent(data) {
    return this._http.put(this.student_url, data, { observe: 'response', responseType: 'json' }).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }
}
