import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {Response} from "@angular/http";
import {Observer, Observable} from "rxjs";
import {HttpInterceptorService} from "./http-interceptor.service";
import {Student} from '../models/Student';

export interface User {
  id          : string
  login       : string
  password    : string
  firstName   : string
  lastName    : string
  email       : string
  langKey     : string
  activated   : boolean
}

@Injectable()
export class UserService {

  students$: Observable<any>;
  private _studentsObserver: Observer<any>;

  users$                      : Observable<User[]>;
  private _usersObserver      : Observer<User[]>;

  user$ : Observable<any>;
  private _userObserver  : Observer<any>;

  private _dataStorage        : {
    users : User[]
    user : User,
    students: Student[]
  };

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
    this.users$ = new Observable<User[]>(observer => {
      this._usersObserver = observer;
    }).share();

    this.user$ = new Observable<User>(observer => {
      this._userObserver = observer;
    }).share();

    this.students$ = new Observable<User>(observer => {
      this._studentsObserver = observer;
    }).share();


    this._dataStorage = { users: [] , user : <User>{}, students: []};
  }

  //Function use to load user data
  loadUser() {
    this._http.get(environment.URL+"/account").subscribe((data : Response) => {
      if(this._userObserver != undefined){
        this._userObserver.next(data.json());
      }
      //console.log("User Received ", data.json());
    }, error => console.log('Could not load users'));
  }

  //Function use to get userComplete By login
  getUserComplete(login) {
    return  this._http.get(environment.URL+"/users/complete/"+login).toPromise()
      .then(this.extractData)
      .catch(this.handleError
      );
  }

  //Function use to load user By login
  loadUserResultById(login) {
    return  this._http.get(environment.URL+"/users/"+login).toPromise()
      .then(this.extractData)
      .catch(this.handleError
      );
  }

  //Function use to load user By id
  getUserById(id) {
    return  this._http.get(environment.URL+"/users/iden/"+id).toPromise()
      .then(this.extractData)
      .catch(this.handleError
      );
  }

  //Function use to load all users
  loadAllUsers(){
    this._http.get(environment.URL+"/users").subscribe((data : Response) => {
      if(this._usersObserver != undefined){
        this._usersObserver.next(data.json());
      }
    }, error => console.log('Could not load users'));
  }

  //Function Use to Login User
  loginUser(user) {

    let extractData = function (res : Response) {
      let body = res.json();
      localStorage.clear();
      let token = JSON.parse(atob(body.id_token.split('.')[1]));
      localStorage.setItem('token_jwt', body.id_token);
      localStorage.setItem('token', token.sub);
      localStorage.setItem('auth', token.auth );
      localStorage.setItem('sAuth', token.sAuth );
      localStorage.setItem('expires', body.exp);
    };
    return this._http.post(environment.URL+"/authenticate",  user).toPromise()
      .then(extractData)
      .catch(this.handleError);
  }

  //Function Use To reset Password
  resetPassword(email) {
    return this._http.post(environment.URL+"/account/reset-password/init/", email).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  //Function Use To create an Account
  signUp(data) {
    return this._http.post(environment.URL+"/register", data).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  //Function use to Activate use Account
  activateAccount(key){
    return this._http.get(environment.URL+"/activate?key="+key).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  //Function Use To create an Account
  changePassword(data) {
    return this._http.post(environment.URL+"/account/reset-password/finish", data).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  //Function use create user
  createUser(data) {
    return this._http.post(environment.URL + "/users", data).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }


  private parseJwt(token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace('-', '+').replace('_', '/');
    return JSON.parse(atob(base64));
  }

  loadAccount(login) {
    return this._http.get(environment.URL + "/users/" + login).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  // Function use to get school Id
  getSchoolId(id) {

    return this._http.get(environment.URL + '/schools/user/' + id).toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  // Function use To get All Students by class id
  getUsersByClassId(idClass) {

    this._http.get(environment.URL  + '/users/class/' + idClass).subscribe(
      (res : Response) => {
        this._dataStorage.students = res.json();
        this._studentsObserver.next(this._dataStorage.students);
      }
    );
  }



}
