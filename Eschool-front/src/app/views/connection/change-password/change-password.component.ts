import { Component, OnInit } from '@angular/core';
import {Subscription} from 'rxjs/Subscription';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../../services/user.service';


@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {

  public keyAndPassword = {
    'key': '',
    'newPassword': ''
  };

  private subscription:Subscription;
  public key;
  param:string;

  constructor(private route:ActivatedRoute, private router:Router, private userService:UserService) {
  }

  ngOnInit() {
    this.subscription = this.route.queryParams.subscribe(
      (queryParam:any) => {
       this.param = queryParam['key']
        this.keyAndPassword.key = this.param
     });
  }

  onSubmit() {

    console.log(JSON.stringify(this.keyAndPassword))
        this.userService.changePassword(this.keyAndPassword).then(
          success => {

            this.router.navigate(['/login'])
          },
          error => {
            console.log(error);
            this.router.navigate(['/500'])
          })
  }
}
