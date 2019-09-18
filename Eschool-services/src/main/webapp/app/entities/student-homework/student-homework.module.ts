import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EschoolSharedModule } from 'app/shared';
import {
    StudentHomeworkComponent,
    StudentHomeworkDetailComponent,
    StudentHomeworkUpdateComponent,
    StudentHomeworkDeletePopupComponent,
    StudentHomeworkDeleteDialogComponent,
    studentHomeworkRoute,
    studentHomeworkPopupRoute
} from './';

const ENTITY_STATES = [...studentHomeworkRoute, ...studentHomeworkPopupRoute];

@NgModule({
    imports: [EschoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StudentHomeworkComponent,
        StudentHomeworkDetailComponent,
        StudentHomeworkUpdateComponent,
        StudentHomeworkDeleteDialogComponent,
        StudentHomeworkDeletePopupComponent
    ],
    entryComponents: [
        StudentHomeworkComponent,
        StudentHomeworkUpdateComponent,
        StudentHomeworkDeleteDialogComponent,
        StudentHomeworkDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EschoolStudentHomeworkModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
