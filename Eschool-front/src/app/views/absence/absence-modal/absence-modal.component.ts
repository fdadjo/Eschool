import {Component, OnDestroy, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {Student} from '../../../models/Student';
import {ItemSelected} from '../../../models/ItemModelSelected';
import {AuthoritiesConstants} from '../../../models/util/authoritiesConstants';
import {ActivatedRoute} from '@angular/router';
import {UserService} from '../../../services/user.service';
import {ClassroomService} from '../../../services/classroom.service';
import {AbsenceService} from '../../../services/absence.service';
import {isNullOrUndefined} from 'util';
import {ValidationAlertComponent} from '../../modal/validation.alert.component';
import {BsModalRef} from 'ngx-bootstrap/modal/bs-modal-ref.service';
import {BsModalService} from 'ngx-bootstrap/modal';
import {ModalService} from '../../../services/modal.service';


@Component({
  selector: 'app-absence-modal',
  templateUrl: './absence-modal.component.html',
  styleUrls: ['./absence-modal.component.css']
})
export class AbsenceModalComponent implements OnInit, OnDestroy {

  private bsModalRef: BsModalRef;

  public absenceData = {
    'id': null,
    'userId': null,
    'schoolId': null,
    'classId': null,
    'userName': '',
    'schoolName': '',
    'className': '',
    'date': '',
    'commentaire': ''
  };

  private param: number;

  private hisEdited = false;
  private isDirector = false;


  public users: Student[] = [];
  public userList: Array<string> = new Array();
  public userSelected: ItemSelected = { 'id'   : '', 'text'  : '' };
  public currentUser: Array<ItemSelected> = new Array();

  public classId;
  public classes = [];
  public classList: Array<string> = new Array();
  public classNameSelected: ItemSelected = { 'id'   : '', 'text'  : '' };
  public currentClassName: Array<ItemSelected> = new Array();

  constructor(private location: Location, private route: ActivatedRoute, private userService: UserService,
              private classroomService: ClassroomService, private absenceService: AbsenceService,
              private modalService: BsModalService, private mService: ModalService) {

    let sAuth: string = localStorage.getItem('auth');
    if (sAuth.localeCompare(AuthoritiesConstants[AuthoritiesConstants.ROLE_ADMIN]) === 0) {
      this.isDirector = true;
    }
    sAuth = localStorage.getItem('sAuth').split(',')[0];
    if ((sAuth.localeCompare(AuthoritiesConstants[AuthoritiesConstants.ROLE_DIRECTOR]) === 0) ||
      (sAuth.localeCompare(AuthoritiesConstants[AuthoritiesConstants.ROLE_FOUNDER]) === 0)) {
      this.isDirector = true;
    } else {
      this.hisEdited = false;
    }

    userService.students$.subscribe( (students) => {
      this.users = students;
      this.loadUsers();
    }, error => console.log('Could not load Students'));
  }

  ngOnInit() {

    this.route.queryParams.subscribe(
      (queryParam: any) => {

        this.param = parseInt(queryParam['id'], 10);
        if (!isNaN(this.param) && !isNullOrUndefined(this.param)) {
          this.hisEdited = true;
          this.absenceService.getAbsenceById(this.param).then(
            (absence: any) => {
              this.absenceData = absence;
              if (localStorage.getItem('schoolId') !== undefined) {
                this.classroomService.getClassroomsBySchoolId(localStorage.getItem('schoolId')).then(
                  (classrooms: any) => {
                    this.classes = classrooms;
                    this.loadClass();
                  }, err => console.log(err)
                );
              }
            }, err => console.log(err)
          );
        } else {
          if (localStorage.getItem('schoolId') !== undefined) {
            this.classroomService.getClassroomsBySchoolId(localStorage.getItem('schoolId')).then(
              (classrooms: any) => {
                this.classes = classrooms;
                this.loadClass();
              }, err => console.log(err)
            );
          }
        }
      }
    );
  }

  ngOnDestroy() {
    this.bsModalRef = null;
  }

  // -------------------------------------------------------------------------------------------------------------------
  loadUsers() {

    this.userList = [];
    this.currentUser = [{'id' : '', 'text' : ''}];
    for (let i = 0; i < this.users.length; i++) {
      this.userList[this.userList.length] = this.users[i].login;
      if (this.users[i].id === this.absenceData.userId) {
        this.absenceData.userName = this.users[i].login;
      }
    }
    this.userList = [...this.userList]; // Very important: This will cause the unit-director-list to detect the change and update.
    this.currentUser = [{'id' : this.absenceData.userName, 'text' : this.absenceData.userName}];
    this.userSelected = {'id' : this.absenceData.userName, 'text' : this.absenceData.userName};
  }

  public getSelectedUser(value: any): void {
    this.userSelected = value;
  }

  public getUserId(userName: string): void {
    for (let i = 0; i < this.users.length; i++) {
      const tempName = this.users[i].login;
      if (tempName === userName) {
        this.absenceData.userId = this.users[i].id;
        this.absenceData.userName = userName;
        return;
      }
    }
  }

  // -------------------------------------------------------------------------------------------------------------------
  loadClass() {

    this.classList = [];
    for (let i = 0; i < this.classes.length; i++) {
      this.classList[this.classList.length] = this.classes[i].classroomName;
      if (this.classes[i].id === this.absenceData.classId) {
        this.absenceData.className = this.classes[i].classroomName;
      }
    }
    this.classList = [...this.classList]; // Very important: This will cause the unit-director-list to detect the change and update.
    this.currentClassName = [{'id' : this.absenceData.className, 'text' : this.absenceData.className}];
    this.getSelectedClassroom({'id' : this.absenceData.className, 'text' : this.absenceData.className});
  }

  public getSelectedClassroom(value: any): void {
    this.classNameSelected = value;
    this.getClassId(this.classNameSelected.id);
    this.userService.getUsersByClassId(this.classId);
  }

  public getClassId(classroomName: string): void {
    for (let i = 0; i < this.classes.length; i++) {
      if (this.classes[i].classroomName === classroomName) {
        this.absenceData.classId = this.classes[i].id;
        this.classId = this.classes[i].id;
        this.absenceData.className = this.classes[i].classroomName;
        return;
      }
    }
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Function use to create one payment
  // -------------------------------------------------------------------------------------------------------------------
  private setModalText(title, body) {
    this.bsModalRef.content.setFields(title, body);
  }
  private closeModal() {
    this.mService.setBoolVal(false);
  }
  public saveAbsence() {

    if (this.userSelected.id !== '' && !isNullOrUndefined(this.userSelected.id)) {
      this.getUserId(this.userSelected.id);
    }

    if (this.classNameSelected.id !== '' && !isNullOrUndefined(this.classNameSelected.id)) {
      this.getClassId(this.classNameSelected.id);
    }

    const initialState = {
      text_body: 'Absence saving...',
      title: 'Absence'
    };
    //this.bsModalRef = this.modalService.show(ValidationAlertComponent, {initialState});
    this.bsModalRef = this.modalService.show(ValidationAlertComponent);
    this.setModalText('Absence', 'Absence saving...');

    if (this.hisEdited) {
      this.absenceService.update(JSON.stringify(this.absenceData)).then(
        success => {
          this.mService.setTextVal('Update successful!');
          console.log(success);
          this.closeModal();
          this.goPrevious();
        },
        error =>  {
          this.mService.setTextVal('Update error: ' + error);
          console.log(error);
          this.closeModal();
        }
      );

    } else {  // Here is the creation absence
      const date = new Date();

      this.absenceData.date = date.toISOString();
      if (localStorage.getItem('schoolId')) {
        this.absenceData.schoolId = Number.parseInt(localStorage.getItem('schoolId'));
      }

      this.absenceService.create(JSON.stringify(this.absenceData)).then(
        success => {
          this.mService.setTextVal('Save successful!');
          console.log(success);
          this.closeModal();
          this.goPrevious();

        },
        error =>  {
          this.mService.setTextVal('Save error: ' + error);
          console.log(error);
          this.closeModal();
        }
      );
    }
  }

  // -------------------------------------------------------------------------------------------------------------------
  goPrevious() {
    this.location.back();
  }

}
