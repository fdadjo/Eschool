import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EschoolSharedModule } from 'app/shared';
import {
    ClassroomComponent,
    ClassroomDetailComponent,
    ClassroomUpdateComponent,
    ClassroomDeletePopupComponent,
    ClassroomDeleteDialogComponent,
    classroomRoute,
    classroomPopupRoute
} from './';

const ENTITY_STATES = [...classroomRoute, ...classroomPopupRoute];

@NgModule({
    imports: [EschoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ClassroomComponent,
        ClassroomDetailComponent,
        ClassroomUpdateComponent,
        ClassroomDeleteDialogComponent,
        ClassroomDeletePopupComponent
    ],
    entryComponents: [ClassroomComponent, ClassroomUpdateComponent, ClassroomDeleteDialogComponent, ClassroomDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EschoolClassroomModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
