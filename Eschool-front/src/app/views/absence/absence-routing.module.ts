import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {AbsenceModalComponent} from './absence-modal/absence-modal.component';
import {AbsenceDirectorComponent} from './absence-director/absence-director.component';
import {AbsenceListComponent} from './absence-list/absence-list.component';

const routes: Routes = [
  { path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full'
  },
  {
    path: 'list',
    component: AbsenceListComponent
  },
  {
    path: 'director',
    component: AbsenceDirectorComponent
  },
  {
    path: 'modal/:id',
    component: AbsenceModalComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AbsenceRoutingModule {}
