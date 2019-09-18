import {CUSTOM_ELEMENTS_SCHEMA, NgModule, NO_ERRORS_SCHEMA} from '@angular/core';

import { CommonModule } from '@angular/common';
import { ConnectionRoutingModule } from './connection-routing.module';
import {RouterModule} from '@angular/router';
import {FormsModule} from '@angular/forms';

import { LandingPageComponent } from './landing-page/landing-page.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { P404Component } from './error/404.component';
import { P500Component } from './error/500.component';
import {SharedModule} from '../../shared.module';
import {HttpModule, Http, XHRBackend, RequestOptions} from '@angular/http';


import { ActivateEmailComponent } from './activate-email/activate-email.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { NewResetPasswordComponent } from './new-reset-password/new-reset-password.component';
import { SuccessComponent } from './success/success.component';

import {UserService} from '../../services/user.service';
import {HttpInterceptorService} from '../../services/http-interceptor.service';

@NgModule({
  imports: [
    CommonModule,
    ConnectionRoutingModule,
    RouterModule,
    HttpModule,
    SharedModule,
    FormsModule,
  ],
  declarations: [
    LandingPageComponent, 
    LoginComponent, 
    RegisterComponent, 
    P404Component,
    P500Component,
    ActivateEmailComponent,
    ResetPasswordComponent,
    ChangePasswordComponent,
    NewResetPasswordComponent,
    SuccessComponent,],
  providers: [
    UserService,
    HttpInterceptorService,
    Http
  ],
  schemas: [
  CUSTOM_ELEMENTS_SCHEMA,
  NO_ERRORS_SCHEMA
]
  
})
export class ConnectionModule { }
