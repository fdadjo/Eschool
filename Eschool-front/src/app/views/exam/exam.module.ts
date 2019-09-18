import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ExamModalComponent } from './exam-modal/exam-modal.component';
import { ExamResultComponent } from './exam-result/exam-result.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [ExamModalComponent, ExamResultComponent]
})
export class ExamModule { }
