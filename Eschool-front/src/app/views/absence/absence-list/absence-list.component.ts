import {Component, OnInit} from '@angular/core';
import {Absence} from '../../../models/Absence';
import {AbsenceService} from '../../../services/absence.service';
import {AuthoritiesConstants} from '../../../models/util/authoritiesConstants';
import {Location} from '@angular/common';
import {Router} from '@angular/router';
import {Pageable} from '../../../models/Pageable';
import {PageableCount} from '../../../models/PageableCount';

@Component({
  selector: 'app-absence-list',
  templateUrl: './absence-list.component.html',
  styleUrls: ['./absence-list.component.scss']
})
export class AbsenceListComponent implements OnInit {

  public absences: Absence[] = [];

  private pageable: Pageable = {
    'page' : 0,
    'size' : PageableCount.SIZE
  };

  public isDirector = false;

  public page = this.pageable.page;
  public itemsPage = this.pageable.size;
  public total = this.pageable.size;

  constructor(private absenceService: AbsenceService, private location: Location, private router: Router) {

    absenceService.absences$.subscribe( (absencePageable: any) => {
      this.absences = absencePageable.content;
      this.total = absencePageable.totalElements;
      // this.absences.map(abs => abs.date = dateUtilitiesService.formatDate(abs.date));
    }, error => console.log('Could not load absences'));

    let auth: string = localStorage.getItem('auth').split(',')[0];
    if (auth.localeCompare(AuthoritiesConstants[AuthoritiesConstants.ROLE_ADMIN]) === 0) {
      this.isDirector = true;
    }
    auth = localStorage.getItem('sAuth').split(',')[0];
    if ((auth.localeCompare(AuthoritiesConstants[AuthoritiesConstants.ROLE_DIRECTOR]) === 0) ||
      (auth.localeCompare(AuthoritiesConstants[AuthoritiesConstants.ROLE_FOUNDER]) === 0)) {
      this.isDirector = true;
    }
  }

  ngOnInit() {
    this.absenceService.getAbsencesForUserId(localStorage.getItem('userId'), this.pageable.page);
  }

  public setPageChanged(page) {
    this.pageable.page = page - 1;
    this.page = page;

    this.absenceService.getAbsencesForUserId(localStorage.getItem('userId'), this.pageable.page);
  }

  showDetailAbsence(id) {
    this.router.navigate(['/absence/modal/:id'], {queryParams : {id : id}});
  }

  goPrevious() {
    this.location.back();
  }

  download() {

    /*var jsPDF = require('jspdf');
     require('jspdf-autotable');
     var columns = ['Date', 'Unity Name', 'Comment'];
     var rows = [];
     for (let i =0;i < this.absences.length; i++) {
     var row = [];
     row[0] = this.absences[i].date;
     row[1] = this.absences[i].unityName;
     row[2] = this.absences[i].commentaire;
     rows[i] = row;
     }
     var doc = new jsPDF('p', 'pt');
     doc.autoTable(columns, rows);
     doc.save('table.pdf');*/
  }

}

