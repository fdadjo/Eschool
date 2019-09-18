import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeworkDirectorComponent } from './homework-director/homework-director.component';
import { HomeworkListComponent } from './homework-list/homework-list.component';
import { HomeworkModalComponent } from './homework-modal/homework-modal.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [HomeworkDirectorComponent, HomeworkListComponent, HomeworkModalComponent]
})
export class HomeworkModule { }
