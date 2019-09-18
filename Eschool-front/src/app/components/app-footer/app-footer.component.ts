import { Component, OnInit } from '@angular/core';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-footer',
  templateUrl: './app-footer.component.html'
})
export class AppFooterComponent {

  public version;
  public currentYear;

  constructor() {
    this.version = environment.VERSION; // 1.0;
  }

  ngOnInit() {
    this.currentYear = new Date().getFullYear();
  }
  
}
