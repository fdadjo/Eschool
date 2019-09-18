import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StudentModalComponent } from './student-modal/student-modal.component';
import { StudentResultComponent } from './student-result/student-result.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [StudentModalComponent, StudentResultComponent]
})
export class StudentsModule { }
