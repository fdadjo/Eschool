import { Component, OnInit } from '@angular/core';
import {AuthoritiesConstants} from '../../models/util/authoritiesConstants';
import {Router} from '@angular/router';
import {AppService} from '../../services/app.service';
import {UserService} from '../../services/user.service';
import {User} from '../../models/User';
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-header',
  templateUrl: './app-header.component.html'
})
export class AppHeaderComponent implements OnInit {

  public user: User = {
    'id' : '',
    'login' : '',
    'password' : '',
    'firstName' : '',
    'lastName' : '',
    'email' : '',
    'langKey' : '',
    'activated' : false,
    'authorities' : []
  };

  public title = '';

  public comment = '';
  public years = [];

  public isAdmin = false;
  public isFounder = false;
  public isDirector = false;
  public isTeacher = false;


  constructor(public router: Router, public _userService: UserService, public appService: AppService,
              public translate: TranslateService) {

    _userService.user$.subscribe((user: any) => {
      this.user = user;

      const auth = localStorage.getItem('auth');
      if (auth.localeCompare(AuthoritiesConstants[AuthoritiesConstants.ROLE_ADMIN]) === 0) {
        this.isAdmin = true;
        this.isFounder = true;
        this.isDirector = true;
        this.isTeacher = true;
      }


      const sAuth: string = localStorage.getItem('sAuth');
      if (sAuth.localeCompare(AuthoritiesConstants[AuthoritiesConstants.ROLE_FOUNDER]) === 0) {
        this.isFounder = true;
        this.isDirector = true;
        this.isTeacher = true;
      }
      if (sAuth.localeCompare(AuthoritiesConstants[AuthoritiesConstants.ROLE_DIRECTOR]) === 0) {
        this.isDirector = true;
        this.isTeacher = true;
      }
      if (sAuth.localeCompare(AuthoritiesConstants[AuthoritiesConstants.ROLE_TEACHER]) === 0) {
        this.isTeacher = true;
      }
    }, error => console.log('Could not get user information'));
  }

  /*
   * WARNING: Attention for the constructor of this unit-director-list, it manage the role for the user connected;
   * don't remove it if you don't know it utility.
   */
  ngOnInit() {
    this._userService.loadUser();
  }

  private chooseLang(lang) {
    this.translate.use(lang);
    // this.translate.setDefaultLang(lang);
  }

  private openMessages() {
    this.router.navigate(['/email']);
  }

  private goToAccount() {
    this.router.navigate(['/schools/modal/:id'], {queryParams : {id : localStorage.getItem('schoolId')}});
  }

  private goToCourseValidation() {
    this.router.navigate(['/course/validation']);
  }

  private logOut() {
    this.user = null;
    localStorage.clear();
    // this.manageLoadindSpinner();
    this.appService.login$.next(false);
    window.location.href = '/#/welcome';
  }
}
