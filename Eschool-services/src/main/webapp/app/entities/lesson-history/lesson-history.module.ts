import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EschoolSharedModule } from 'app/shared';
import {
    LessonHistoryComponent,
    LessonHistoryDetailComponent,
    LessonHistoryUpdateComponent,
    LessonHistoryDeletePopupComponent,
    LessonHistoryDeleteDialogComponent,
    lessonHistoryRoute,
    lessonHistoryPopupRoute
} from './';

const ENTITY_STATES = [...lessonHistoryRoute, ...lessonHistoryPopupRoute];

@NgModule({
    imports: [EschoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LessonHistoryComponent,
        LessonHistoryDetailComponent,
        LessonHistoryUpdateComponent,
        LessonHistoryDeleteDialogComponent,
        LessonHistoryDeletePopupComponent
    ],
    entryComponents: [
        LessonHistoryComponent,
        LessonHistoryUpdateComponent,
        LessonHistoryDeleteDialogComponent,
        LessonHistoryDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EschoolLessonHistoryModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
