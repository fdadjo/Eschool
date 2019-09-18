import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EschoolSharedModule } from 'app/shared';
import {
    ExamResultComponent,
    ExamResultDetailComponent,
    ExamResultUpdateComponent,
    ExamResultDeletePopupComponent,
    ExamResultDeleteDialogComponent,
    examResultRoute,
    examResultPopupRoute
} from './';

const ENTITY_STATES = [...examResultRoute, ...examResultPopupRoute];

@NgModule({
    imports: [EschoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ExamResultComponent,
        ExamResultDetailComponent,
        ExamResultUpdateComponent,
        ExamResultDeleteDialogComponent,
        ExamResultDeletePopupComponent
    ],
    entryComponents: [ExamResultComponent, ExamResultUpdateComponent, ExamResultDeleteDialogComponent, ExamResultDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EschoolExamResultModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
