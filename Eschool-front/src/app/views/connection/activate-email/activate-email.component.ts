import { Component, OnInit } from '@angular/core';
import {Subscription} from 'rxjs/Subscription';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../../services/user.service';

@Component({
  selector: 'app-activate-email',
  templateUrl: './activate-email.component.html',
  styleUrls: ['./activate-email.component.scss']
})
export class ActivateEmailComponent implements OnInit {

  private subscription: Subscription;
  public key;
  param: string;

  constructor(private route: ActivatedRoute, private router: Router, private userService: UserService) {


  }

  ngOnInit() {
    
  }

  onSubmit() {
    this.subscription = this.route.queryParams.subscribe(
      (queryParam: any) => {
        this.param = queryParam['key'];
        this.userService.activateAccount(this.param).then(
          success => {
            console.log(success);
            this.router.navigate(['/login'])
          },
          error =>{
            console.log(error);
            this.router.navigate(['/500'])
          }
        );
      });
  }

}
