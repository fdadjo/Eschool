import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SchoolListComponent } from './school-list/school-list.component';
import { SchoolSingleComponent } from './school-single/school-single.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [SchoolListComponent, SchoolSingleComponent]
})
export class SchoolsModule { }
