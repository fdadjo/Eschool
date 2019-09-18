import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILessonHistoryPreparation } from 'app/shared/model/lesson-history-preparation.model';

@Component({
    selector: 'jhi-lesson-history-preparation-detail',
    templateUrl: './lesson-history-preparation-detail.component.html'
})
export class LessonHistoryPreparationDetailComponent implements OnInit {
    lessonHistoryPreparation: ILessonHistoryPreparation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lessonHistoryPreparation }) => {
            this.lessonHistoryPreparation = lessonHistoryPreparation;
        });
    }

    previousState() {
        window.history.back();
    }
}
