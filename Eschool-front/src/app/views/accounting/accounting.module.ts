import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccountingDirectorComponent } from './accounting-director/accounting-director.component';
import { AccountingListComponent } from './accounting-list/accounting-list.component';
import { AccountingModalComponent } from './accounting-modal/accounting-modal.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [AccountingDirectorComponent, AccountingListComponent, AccountingModalComponent]
})
export class AccountingModule { }
