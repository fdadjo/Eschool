import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EschoolSharedModule } from 'app/shared';
import {
    HomeworkComponent,
    HomeworkDetailComponent,
    HomeworkUpdateComponent,
    HomeworkDeletePopupComponent,
    HomeworkDeleteDialogComponent,
    homeworkRoute,
    homeworkPopupRoute
} from './';

const ENTITY_STATES = [...homeworkRoute, ...homeworkPopupRoute];

@NgModule({
    imports: [EschoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HomeworkComponent,
        HomeworkDetailComponent,
        HomeworkUpdateComponent,
        HomeworkDeleteDialogComponent,
        HomeworkDeletePopupComponent
    ],
    entryComponents: [HomeworkComponent, HomeworkUpdateComponent, HomeworkDeleteDialogComponent, HomeworkDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EschoolHomeworkModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
