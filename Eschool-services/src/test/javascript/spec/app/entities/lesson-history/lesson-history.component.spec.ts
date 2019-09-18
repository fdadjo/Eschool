/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EschoolTestModule } from '../../../test.module';
import { LessonHistoryComponent } from 'app/entities/lesson-history/lesson-history.component';
import { LessonHistoryService } from 'app/entities/lesson-history/lesson-history.service';
import { LessonHistory } from 'app/shared/model/lesson-history.model';

describe('Component Tests', () => {
    describe('LessonHistory Management Component', () => {
        let comp: LessonHistoryComponent;
        let fixture: ComponentFixture<LessonHistoryComponent>;
        let service: LessonHistoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [LessonHistoryComponent],
                providers: []
            })
                .overrideTemplate(LessonHistoryComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LessonHistoryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LessonHistoryService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new LessonHistory(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.lessonHistories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
