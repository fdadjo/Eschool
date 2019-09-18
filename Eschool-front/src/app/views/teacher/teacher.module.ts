import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TeacherListComponent } from './teacher-list/teacher-list.component';
import { TeacherModalComponent } from './teacher-modal/teacher-modal.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [TeacherListComponent, TeacherModalComponent]
})
export class TeacherModule { }
