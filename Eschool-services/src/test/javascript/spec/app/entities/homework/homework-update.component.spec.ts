/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EschoolTestModule } from '../../../test.module';
import { HomeworkUpdateComponent } from 'app/entities/homework/homework-update.component';
import { HomeworkService } from 'app/entities/homework/homework.service';
import { Homework } from 'app/shared/model/homework.model';

describe('Component Tests', () => {
    describe('Homework Management Update Component', () => {
        let comp: HomeworkUpdateComponent;
        let fixture: ComponentFixture<HomeworkUpdateComponent>;
        let service: HomeworkService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [HomeworkUpdateComponent]
            })
                .overrideTemplate(HomeworkUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HomeworkUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HomeworkService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Homework(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.homework = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Homework();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.homework = entity;
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
