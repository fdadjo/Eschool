/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EschoolTestModule } from '../../../test.module';
import { LessonHistoryPreparationUpdateComponent } from 'app/entities/lesson-history-preparation/lesson-history-preparation-update.component';
import { LessonHistoryPreparationService } from 'app/entities/lesson-history-preparation/lesson-history-preparation.service';
import { LessonHistoryPreparation } from 'app/shared/model/lesson-history-preparation.model';

describe('Component Tests', () => {
    describe('LessonHistoryPreparation Management Update Component', () => {
        let comp: LessonHistoryPreparationUpdateComponent;
        let fixture: ComponentFixture<LessonHistoryPreparationUpdateComponent>;
        let service: LessonHistoryPreparationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [LessonHistoryPreparationUpdateComponent]
            })
                .overrideTemplate(LessonHistoryPreparationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LessonHistoryPreparationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LessonHistoryPreparationService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new LessonHistoryPreparation(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.lessonHistoryPreparation = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new LessonHistoryPreparation();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.lessonHistoryPreparation = entity;
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
