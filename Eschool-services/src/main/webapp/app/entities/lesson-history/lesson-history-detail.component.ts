import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILessonHistory } from 'app/shared/model/lesson-history.model';

@Component({
    selector: 'jhi-lesson-history-detail',
    templateUrl: './lesson-history-detail.component.html'
})
export class LessonHistoryDetailComponent implements OnInit {
    lessonHistory: ILessonHistory;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lessonHistory }) => {
            this.lessonHistory = lessonHistory;
        });
    }

    previousState() {
        window.history.back();
    }
}
