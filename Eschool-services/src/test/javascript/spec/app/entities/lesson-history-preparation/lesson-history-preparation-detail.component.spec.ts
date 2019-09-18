/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EschoolTestModule } from '../../../test.module';
import { LessonHistoryPreparationDetailComponent } from 'app/entities/lesson-history-preparation/lesson-history-preparation-detail.component';
import { LessonHistoryPreparation } from 'app/shared/model/lesson-history-preparation.model';

describe('Component Tests', () => {
    describe('LessonHistoryPreparation Management Detail Component', () => {
        let comp: LessonHistoryPreparationDetailComponent;
        let fixture: ComponentFixture<LessonHistoryPreparationDetailComponent>;
        const route = ({ data: of({ lessonHistoryPreparation: new LessonHistoryPreparation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [LessonHistoryPreparationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LessonHistoryPreparationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LessonHistoryPreparationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.lessonHistoryPreparation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
