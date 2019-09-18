/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EschoolTestModule } from '../../../test.module';
import { LessonHistoryUpdateComponent } from 'app/entities/lesson-history/lesson-history-update.component';
import { LessonHistoryService } from 'app/entities/lesson-history/lesson-history.service';
import { LessonHistory } from 'app/shared/model/lesson-history.model';

describe('Component Tests', () => {
    describe('LessonHistory Management Update Component', () => {
        let comp: LessonHistoryUpdateComponent;
        let fixture: ComponentFixture<LessonHistoryUpdateComponent>;
        let service: LessonHistoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [LessonHistoryUpdateComponent]
            })
                .overrideTemplate(LessonHistoryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LessonHistoryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LessonHistoryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new LessonHistory(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.lessonHistory = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new LessonHistory();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.lessonHistory = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
