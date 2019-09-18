import { Component, OnInit } from '@angular/core';
import {UserService} from '../../../services/user.service';
import {Router} from '@angular/router';
import {Pageable} from '../../../models/Pageable';
import {PageableCount} from '../../../models/PageableCount';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  public userAccount = {
    'email': '',
    'firstName': '',
    'lastName': '',
    'langKey': 'en',
    'login': '',
    'coefCc': 0,
    'exam': 0,
    'schoolName': '',
    'password': ''
  };

  private pageable: Pageable = {
    'page' : 1,
    'size' : PageableCount.SIZE
  };

  public confirmPassword = '';

  constructor(public router: Router, private userService: UserService) { }

  ngOnInit() {}


  onSubmit() {
    var  userLogin = {
      'password': this.userAccount.password,
      'rememberMe': true,
      'username': this.userAccount.login
    };
    
    
    if (this.userAccount.password === this.confirmPassword) {
      this.userService.signUp(this.userAccount).then(
        success => {
          //this.login(userLogin);
          
          this.router.navigate(['/success'])
        },
        error =>  {
          console.log(error);
        }
      );
      this.userAccount = {
        'email': '',
        'firstName': '',
        'lastName': '',
        'langKey': 'en',
        'login': '',
        'coefCc': 0,
        'exam': 0,
        'schoolName': '',
        'password': ''
      };
      this.confirmPassword = '';


    } else {
      console.log('Password are not the same !!!!!');
    }
  }

}
