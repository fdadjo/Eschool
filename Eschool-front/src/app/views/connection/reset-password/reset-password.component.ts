import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from '../../../services/user.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {
  
  public email = " "

  constructor(public router: Router, private userService: UserService) { }

  ngOnInit() {
  }

  onSubmit() {

    this.userService.resetPassword(this.email).then(
      success => {
        
        this.router.navigate(['/success'])
      },
      error =>  {
        console.log(error);
      }
    )
  }

}
