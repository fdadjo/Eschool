/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EschoolTestModule } from '../../../test.module';
import { LessonHistoryPreparationComponent } from 'app/entities/lesson-history-preparation/lesson-history-preparation.component';
import { LessonHistoryPreparationService } from 'app/entities/lesson-history-preparation/lesson-history-preparation.service';
import { LessonHistoryPreparation } from 'app/shared/model/lesson-history-preparation.model';

describe('Component Tests', () => {
    describe('LessonHistoryPreparation Management Component', () => {
        let comp: LessonHistoryPreparationComponent;
        let fixture: ComponentFixture<LessonHistoryPreparationComponent>;
        let service: LessonHistoryPreparationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [LessonHistoryPreparationComponent],
                providers: []
            })
                .overrideTemplate(LessonHistoryPreparationComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LessonHistoryPreparationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LessonHistoryPreparationService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new LessonHistoryPreparation(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.lessonHistoryPreparations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
