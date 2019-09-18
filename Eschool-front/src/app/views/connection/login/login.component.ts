import { Component, OnInit } from '@angular/core';
import {UserService} from '../../../services/user.service';
import {Pageable} from '../../../models/Pageable';
import {PageableCount} from '../../../models/PageableCount';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  public userLogin = {
    "password": "",
    "rememberMe": "true",
    "username": ""
  };

  
  private pageable: Pageable = {
    'page' : 0,
    'size' : PageableCount.SIZE
  };

  constructor(public router: Router, private userService: UserService) { }

  ngOnInit() {
  }

  onSubmit() {
    this.userService.loginUser(this.userLogin).then(
      success => {

        if (localStorage.getItem('token') !== undefined) {
          this.userService.loadAccount(localStorage.getItem('token')).then(
            (response: any) => {
              localStorage.setItem('userId', response.id)
              localStorage.setItem('auth', response.authorities.toString() );

              this.userService.getSchoolId(response.id).then(
                (result: any) => {
                  if (result[0] !== undefined) {
                    localStorage.setItem('schoolId', result[0].id);
                    //localStorage.setItem('picture_link', result[0].logoLink);
                  }
                  
                  this.router.navigateByUrl('/');
                },
                error =>  {
                  console.log(error);
                }
              );
            },
            error =>  {
              console.log(error)
            }
          );
        }

      },
      error =>  {
        console.log(error);
        localStorage.clear();
        this.userLogin = {
          "password": "",
          "rememberMe": "true",
          "username": ""
        };
      }
    );
  }

}

