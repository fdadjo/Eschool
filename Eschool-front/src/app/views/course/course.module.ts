import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CourseModalComponent } from './course-modal/course-modal.component';
import { CourseValidationComponent } from './course-validation/course-validation.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [CourseModalComponent, CourseValidationComponent]
})
export class CourseModule { }
