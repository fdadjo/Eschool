import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAbscence } from 'app/shared/model/abscence.model';

@Component({
    selector: 'jhi-abscence-detail',
    templateUrl: './abscence-detail.component.html'
})
export class AbscenceDetailComponent implements OnInit {
    abscence: IAbscence;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ abscence }) => {
            this.abscence = abscence;
        });
    }

    previousState() {
        window.history.back();
    }
}
