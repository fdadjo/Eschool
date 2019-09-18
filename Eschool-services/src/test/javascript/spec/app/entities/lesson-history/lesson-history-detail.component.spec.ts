/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EschoolTestModule } from '../../../test.module';
import { LessonHistoryDetailComponent } from 'app/entities/lesson-history/lesson-history-detail.component';
import { LessonHistory } from 'app/shared/model/lesson-history.model';

describe('Component Tests', () => {
    describe('LessonHistory Management Detail Component', () => {
        let comp: LessonHistoryDetailComponent;
        let fixture: ComponentFixture<LessonHistoryDetailComponent>;
        const route = ({ data: of({ lessonHistory: new LessonHistory(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [LessonHistoryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LessonHistoryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LessonHistoryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.lessonHistory).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
