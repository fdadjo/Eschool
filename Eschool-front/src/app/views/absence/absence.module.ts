import {CUSTOM_ELEMENTS_SCHEMA, NgModule, NO_ERRORS_SCHEMA} from '@angular/core';

import { CommonModule } from '@angular/common';
import { AbsenceDirectorComponent } from './absence-director/absence-director.component';
import { AbsenceListComponent } from './absence-list/absence-list.component';
import { AbsenceModalComponent } from './absence-modal/absence-modal.component';

import {AbsenceRoutingModule} from './absence-routing.module';
import {AbsenceService} from '../../services/absence.service';
import {SharedModule} from '../../shared.module';
import {ClassroomService} from '../../services/classroom.service';
import {StudentService} from '../../services/student.service';
import {NgxPaginationModule} from 'ngx-pagination';
import {CourseService} from '../../services/course.service';
import {SelectModule} from 'ng2-select';
import {ValidationAlertModule} from '../modal/validation.alert.module';
import {ValidationAlertComponent} from '../modal/validation.alert.component';
import {FormsModule} from '@angular/forms';
import {ModalService} from '../../services/modal.service';


@NgModule({
  imports: [
    AbsenceRoutingModule,
    SelectModule,
    NgxPaginationModule,
    SharedModule,
    FormsModule,
    ValidationAlertModule
  ],
  declarations: [
    AbsenceListComponent,
    AbsenceDirectorComponent,
    AbsenceModalComponent
  ],
  providers: [
    AbsenceService,
    ClassroomService,
    StudentService,
    CourseService,
    ModalService
  ],
  entryComponents: [
    ValidationAlertComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA]
})
export class AbsenceModule { }
