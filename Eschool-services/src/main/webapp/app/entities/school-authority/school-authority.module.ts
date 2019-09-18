import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EschoolSharedModule } from 'app/shared';
import {
    SchoolAuthorityComponent,
    SchoolAuthorityDetailComponent,
    SchoolAuthorityUpdateComponent,
    SchoolAuthorityDeletePopupComponent,
    SchoolAuthorityDeleteDialogComponent,
    schoolAuthorityRoute,
    schoolAuthorityPopupRoute
} from './';

const ENTITY_STATES = [...schoolAuthorityRoute, ...schoolAuthorityPopupRoute];

@NgModule({
    imports: [EschoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SchoolAuthorityComponent,
        SchoolAuthorityDetailComponent,
        SchoolAuthorityUpdateComponent,
        SchoolAuthorityDeleteDialogComponent,
        SchoolAuthorityDeletePopupComponent
    ],
    entryComponents: [
        SchoolAuthorityComponent,
        SchoolAuthorityUpdateComponent,
        SchoolAuthorityDeleteDialogComponent,
        SchoolAuthorityDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EschoolSchoolAuthorityModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
