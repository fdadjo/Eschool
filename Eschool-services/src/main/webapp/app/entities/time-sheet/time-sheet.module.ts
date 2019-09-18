import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EschoolSharedModule } from 'app/shared';
import {
    TimeSheetComponent,
    TimeSheetDetailComponent,
    TimeSheetUpdateComponent,
    TimeSheetDeletePopupComponent,
    TimeSheetDeleteDialogComponent,
    timeSheetRoute,
    timeSheetPopupRoute
} from './';

const ENTITY_STATES = [...timeSheetRoute, ...timeSheetPopupRoute];

@NgModule({
    imports: [EschoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TimeSheetComponent,
        TimeSheetDetailComponent,
        TimeSheetUpdateComponent,
        TimeSheetDeleteDialogComponent,
        TimeSheetDeletePopupComponent
    ],
    entryComponents: [TimeSheetComponent, TimeSheetUpdateComponent, TimeSheetDeleteDialogComponent, TimeSheetDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EschoolTimeSheetModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
