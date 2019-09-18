import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EschoolSharedModule } from 'app/shared';
import {
    AbscenceComponent,
    AbscenceDetailComponent,
    AbscenceUpdateComponent,
    AbscenceDeletePopupComponent,
    AbscenceDeleteDialogComponent,
    abscenceRoute,
    abscencePopupRoute
} from './';

const ENTITY_STATES = [...abscenceRoute, ...abscencePopupRoute];

@NgModule({
    imports: [EschoolSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AbscenceComponent,
        AbscenceDetailComponent,
        AbscenceUpdateComponent,
        AbscenceDeleteDialogComponent,
        AbscenceDeletePopupComponent
    ],
    entryComponents: [AbscenceComponent, AbscenceUpdateComponent, AbscenceDeleteDialogComponent, AbscenceDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EschoolAbscenceModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
