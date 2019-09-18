import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DirectorListComponent } from './director-list/director-list.component';
import { DirectorModalComponent } from './director-modal/director-modal.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [DirectorListComponent, DirectorModalComponent]
})
export class DirectorModule { }
