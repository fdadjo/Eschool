import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISchoolAuthority } from 'app/shared/model/school-authority.model';

@Component({
    selector: 'jhi-school-authority-detail',
    templateUrl: './school-authority-detail.component.html'
})
export class SchoolAuthorityDetailComponent implements OnInit {
    schoolAuthority: ISchoolAuthority;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ schoolAuthority }) => {
            this.schoolAuthority = schoolAuthority;
        });
    }

    previousState() {
        window.history.back();
    }
}
