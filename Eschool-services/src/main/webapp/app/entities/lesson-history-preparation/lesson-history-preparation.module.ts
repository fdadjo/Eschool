import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EschoolSharedModule } from 'app/shared';
import {
    LessonHistoryPreparationComponent,
    LessonHistoryPreparationDetailComponent,
    LessonHistoryPreparationUpdateComponent,
    LessonHistoryPreparationDeletePopupComponent,
    LessonHistoryPreparationDeleteDialogComponent,
    lessonHistoryPreparationRoute,
    lessonHistoryPreparationPopupRoute
} from './';

const ENTITY_STATES = [...lessonHistoryPreparationRoute, ...lessonHistoryPreparationPopupRoute];

@NgModule({
    imports: [EschoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LessonHistoryPreparationComponent,
        LessonHistoryPreparationDetailComponent,
        LessonHistoryPreparationUpdateComponent,
        LessonHistoryPreparationDeleteDialogComponent,
        LessonHistoryPreparationDeletePopupComponent
    ],
    entryComponents: [
        LessonHistoryPreparationComponent,
        LessonHistoryPreparationUpdateComponent,
        LessonHistoryPreparationDeleteDialogComponent,
        LessonHistoryPreparationDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EschoolLessonHistoryPreparationModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
