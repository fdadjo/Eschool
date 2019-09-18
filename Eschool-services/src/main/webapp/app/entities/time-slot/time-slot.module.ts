import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EschoolSharedModule } from 'app/shared';
import {
    TimeSlotComponent,
    TimeSlotDetailComponent,
    TimeSlotUpdateComponent,
    TimeSlotDeletePopupComponent,
    TimeSlotDeleteDialogComponent,
    timeSlotRoute,
    timeSlotPopupRoute
} from './';

const ENTITY_STATES = [...timeSlotRoute, ...timeSlotPopupRoute];

@NgModule({
    imports: [EschoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TimeSlotComponent,
        TimeSlotDetailComponent,
        TimeSlotUpdateComponent,
        TimeSlotDeleteDialogComponent,
        TimeSlotDeletePopupComponent
    ],
    entryComponents: [TimeSlotComponent, TimeSlotUpdateComponent, TimeSlotDeleteDialogComponent, TimeSlotDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EschoolTimeSlotModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
