import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EschoolSharedModule } from 'app/shared';
import {
    TimeSlotHistoryComponent,
    TimeSlotHistoryDetailComponent,
    TimeSlotHistoryUpdateComponent,
    TimeSlotHistoryDeletePopupComponent,
    TimeSlotHistoryDeleteDialogComponent,
    timeSlotHistoryRoute,
    timeSlotHistoryPopupRoute
} from './';

const ENTITY_STATES = [...timeSlotHistoryRoute, ...timeSlotHistoryPopupRoute];

@NgModule({
    imports: [EschoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TimeSlotHistoryComponent,
        TimeSlotHistoryDetailComponent,
        TimeSlotHistoryUpdateComponent,
        TimeSlotHistoryDeleteDialogComponent,
        TimeSlotHistoryDeletePopupComponent
    ],
    entryComponents: [
        TimeSlotHistoryComponent,
        TimeSlotHistoryUpdateComponent,
        TimeSlotHistoryDeleteDialogComponent,
        TimeSlotHistoryDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EschoolTimeSlotHistoryModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
