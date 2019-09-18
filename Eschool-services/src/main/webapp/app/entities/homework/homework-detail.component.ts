import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHomework } from 'app/shared/model/homework.model';

@Component({
    selector: 'jhi-homework-detail',
    templateUrl: './homework-detail.component.html'
})
export class HomeworkDetailComponent implements OnInit {
    homework: IHomework;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ homework }) => {
            this.homework = homework;
        });
    }

    previousState() {
        window.history.back();
    }
}
