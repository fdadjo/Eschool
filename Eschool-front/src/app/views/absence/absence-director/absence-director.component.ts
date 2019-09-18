import { Component, OnInit } from '@angular/core';
import {PageableCount} from '../../../models/PageableCount';
import {Pageable} from '../../../models/Pageable';
import {ClassroomService} from '../../../services/classroom.service';
import {StudentService} from '../../../services/student.service';
import {AbsenceService} from '../../../services/absence.service';
import {Location} from '@angular/common';
import {AuthoritiesConstants} from '../../../models/util/authoritiesConstants';
import {Absence} from '../../../models/Absence';
import {Router} from '@angular/router';

interface CurrentClass {
  id: number;
  classroomName: string;
  studentNum: number;
  fees: number;
  activate: boolean;
}

@Component({
  selector: 'app-absence-director',
  templateUrl: './absence-director.component.html'
})
export class AbsenceDirectorComponent implements OnInit {

  public classrooms: CurrentClass[] = [];
  public students = [];
  public absences: Absence[] = [];
  public currentClassId = 0;
  public currentStudId = 0;
  public isDirector = false;

  private pageable: Pageable = {
    'page' : 0,
    'size' : PageableCount.SIZE
  };

  public page = this.pageable.page;
  public itemsPage = this.pageable.size;
  public total = this.pageable.size;


  constructor(private absenceService: AbsenceService, private classroomService: ClassroomService, private location: Location,
              private studService: StudentService, private router: Router) {

    let auth: string = localStorage.getItem('auth');
    if (auth.localeCompare(AuthoritiesConstants[AuthoritiesConstants.ROLE_ADMIN]) === 0) {
      this.isDirector = true;
    }
    auth = localStorage.getItem('sAuth').split(',')[0];
    if ((auth.localeCompare(AuthoritiesConstants[AuthoritiesConstants.ROLE_DIRECTOR]) === 0) ||
      (auth.localeCompare(AuthoritiesConstants[AuthoritiesConstants.ROLE_FOUNDER]) === 0)) {
      this.isDirector = true;
    }

    this.classroomService.classroom$.subscribe( (classroom) => {
      this.classrooms = classroom.content;
      this.computeClassAbsences();
      this.total = classroom.totalElements;
    }, error => {
      console.log('Could not load classroom')
    })
  }

  ngOnInit() {
    this.classroomService.getClassroomsBySchoolIdAndPage(localStorage.getItem('schoolId'), this.pageable.page);
  }

  public setPageChanged(page) {
    this.pageable.page = page - 1;
    this.page = page;
    this.classroomService.getClassroomsBySchoolIdAndPage(localStorage.getItem('schoolId'), this.pageable.page);
  }

  // -------------------------------------------------------------------------------------------------------------------
  computeClassAbsences() {
    for (let j = 0; j < this.classrooms.length; j++) {
      this.absenceService.getFullClassAbsences(this.classrooms[j].id).then(
        (succes: any[]) => {
          this.classrooms[j].fees = succes.length;
          this.classrooms[j].activate = false;
        }, error => console.log('Unable to load absences class')
      );
    }
  }

  // -------------------------------------------------------------------------------------------------------------------
  openClassList(classroom) {
    if (!classroom.activate && classroom.id !== this.currentClassId) {
      this.studService.getStudentByClassId(classroom.id).then(
        (succes: any) => {
          for (let i = 0; i < this.classrooms.length; i++) {
            if (this.currentClassId === this.classrooms[i].id) {
              this.classrooms[i].activate = !this.classrooms[i].activate;
              break;
            }
          }
          this.students = succes;
          this.computeStudentAbsences();
          classroom.activate = !classroom.activate;
          this.currentClassId = classroom.id;
        }, error => console.log('Unable to load student class')
      );
    } else {
      classroom.activate = !classroom.activate;
      this.currentClassId = 0;
      this.currentStudId = 0;
    }
  }

  // -------------------------------------------------------------------------------------------------------------------
  computeStudentAbsences() {
    for (let j = 0; j < this.students.length; j++) {
      this.absenceService.getFullUserAbsences(this.students[j].id).then(
        (succes: any[]) => {
          this.students[j].cniNumber = succes.length;
          this.students[j].activated = false;
        }, error => console.log('Unable to load absences student')
      );
    }
  }

  // -------------------------------------------------------------------------------------------------------------------
  openStudentList(student) {
    if (!student.activated && student.id !== this.currentStudId) {
      this.absenceService.getFullUserAbsences(student.id).then(
        (succes: any) => {
          for (let i = 0; i < this.students.length; i++) {
            if (this.currentStudId === this.students[i].id) {
              this.students[i].activated = !this.students[i].activated;
              break;
            }
          }
          this.absences = succes;
          /*this.absences.map(abs => {
           if (abs.date) {
           abs.date = this.dateUtilitiesService.formatDate(abs.date);
           }
           });*/
          this.currentStudId = student.id;
          student.activated = !student.activated;
        }, error => console.log('Unable to load student absences')
      );
    } else {
      student.activated = !student.activated;
      this.currentStudId = 0;
    }
  }

  private goPrevious() {
    this.location.back();
  }

  // -------------------------------------------------------------------------------------------------------------------
  openAbsence(paymentId) {
    this.router.navigate(['/absence/modal/:id'], {queryParams : {id : paymentId}});
  }

  newAbsence() {
    this.router.navigate(['/absence/modal/:id'], {queryParams : {id : null}});
  }

}
