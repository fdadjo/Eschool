import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UnitListComponent } from './unit-list/unit-list.component';
import { UnitModalComponent } from './unit-modal/unit-modal.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [UnitListComponent, UnitModalComponent]
})
export class UnitModule { }
