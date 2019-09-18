import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TimesheetListComponent } from './timesheet-list/timesheet-list.component';
import { TimesheetModalComponent } from './timesheet-modal/timesheet-modal.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [TimesheetListComponent, TimesheetModalComponent]
})
export class TimesheetModule { }
