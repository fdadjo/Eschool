import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {LandingPageComponent} from './landing-page/landing-page.component'
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { P404Component } from './error/404.component';
import { P500Component } from './error/500.component';
import { ActivateEmailComponent } from './activate-email/activate-email.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { NewResetPasswordComponent } from './new-reset-password/new-reset-password.component';
import { SuccessComponent } from './success/success.component';

import { Routes, RouterModule } from '@angular/router'

const routes: Routes = [
  {
    path: 'welcome',
    component: LandingPageComponent,
    data: {
      title: 'Page Landing'
    }
  },
  {
    path: '404',
    component: P404Component,
    data: {
      title: 'Page 404'
    }
  },
  {
    path: '500',
    component: P500Component,
    data: {
      title: 'Page 500'
    }
  },
  {
    path: 'login',
    component: LoginComponent,
    data: {
      title: 'Login Page'
    }
  },
  {
    path: 'activate',
    component: ActivateEmailComponent,
    data: {
      title: 'Page mail activation'
    }
  },
  {
    path: 'change-password',
    component: ChangePasswordComponent,
    data: {
      title: 'Page change email'
    }
  },
  {
    path: 'success',
    component: SuccessComponent,
    data: {
      title: 'Page success'
    }
  },
  {
    path: 'reset-password',
    component: ResetPasswordComponent,
    data: {
      title: 'Page reset password'
    }
  },
  {
    path: 'reset-new-password',
    component: NewResetPasswordComponent,
    data: {
      title: 'Page reset new password email'
    }
  },
  {
    path: 'register',
    component: RegisterComponent,
    data: {
      title: 'Register Page'
    }
  }
];

@NgModule({
  imports: [
    CommonModule, RouterModule.forChild(routes)
  ],
  declarations: []
})
export class ConnectionRoutingModule { }
