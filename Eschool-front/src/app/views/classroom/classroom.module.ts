import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClassroomDetailComponent } from './classroom-detail/classroom-detail.component';
import { ClassroomModalComponent } from './classroom-modal/classroom-modal.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [ClassroomDetailComponent, ClassroomModalComponent]
})
export class ClassroomModule { }
