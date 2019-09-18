import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClassroomHistory } from 'app/shared/model/classroom-history.model';

@Component({
    selector: 'jhi-classroom-history-detail',
    templateUrl: './classroom-history-detail.component.html'
})
export class ClassroomHistoryDetailComponent implements OnInit {
    classroomHistory: IClassroomHistory;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ classroomHistory }) => {
            this.classroomHistory = classroomHistory;
        });
    }

    previousState() {
        window.history.back();
    }
}
