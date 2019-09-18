import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EschoolSharedModule } from 'app/shared';
import {
    ClassroomHistoryComponent,
    ClassroomHistoryDetailComponent,
    ClassroomHistoryUpdateComponent,
    ClassroomHistoryDeletePopupComponent,
    ClassroomHistoryDeleteDialogComponent,
    classroomHistoryRoute,
    classroomHistoryPopupRoute
} from './';

const ENTITY_STATES = [...classroomHistoryRoute, ...classroomHistoryPopupRoute];

@NgModule({
    imports: [EschoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ClassroomHistoryComponent,
        ClassroomHistoryDetailComponent,
        ClassroomHistoryUpdateComponent,
        ClassroomHistoryDeleteDialogComponent,
        ClassroomHistoryDeletePopupComponent
    ],
    entryComponents: [
        ClassroomHistoryComponent,
        ClassroomHistoryUpdateComponent,
        ClassroomHistoryDeleteDialogComponent,
        ClassroomHistoryDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EschoolClassroomHistoryModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
