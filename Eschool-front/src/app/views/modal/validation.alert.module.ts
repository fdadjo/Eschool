// Angular
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

// Modal Component
import { ModalModule } from 'ngx-bootstrap/modal';
import {ValidationAlertComponent} from './validation.alert.component';

@NgModule({
  imports: [
    CommonModule,
    ModalModule.forRoot()
  ],
  declarations: [
    ValidationAlertComponent
  ]
})
export class ValidationAlertModule { }
