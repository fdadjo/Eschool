import {Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import {AuthoritiesConstants} from '../../models/util/authoritiesConstants';

@Component({
  templateUrl: 'dashboard.component.html'
})
export class DashboardComponent implements OnInit{


  public isAdmin = false;
  public isFounder = false;
  public isDirector = false;
  public isTeacher = false;

  constructor(private router: Router) {

    const auth: string = localStorage.getItem('auth');  // user or admin
    if (auth.localeCompare(AuthoritiesConstants[AuthoritiesConstants.ROLE_ADMIN]) === 0) {
      this.isAdmin = true;
      this.isFounder = true;
      this.isDirector = true;
      this.isTeacher = true;
    }

    // different type of users
    const sAuth: string = localStorage.getItem('sAuth').split(',')[0];
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
  }

  ngOnInit() {
  }

  openPage(namePage) {
    console.log("Hello Everyone")
    if (namePage === 'absence') {
      if (this.isDirector) {
        this.router.navigateByUrl('/absence/director');
      } else {
        this.router.navigateByUrl('/absence/list');
      }
    } else if (namePage === 'accounting') {
      if (this.isDirector) {
        this.router.navigateByUrl('/accounting/director');
      }
    } else if (namePage === 'homework') {
      if (this.isDirector) {
        this.router.navigateByUrl('/homework/director');
      } else {
        this.router.navigateByUrl('/homework/list');
      }
    } else if (namePage === 'homework' && this.isTeacher) {
      this.router.navigateByUrl('/homework-teacher');
    } else {
      this.router.navigateByUrl('/' + namePage);
    }
  }
}
