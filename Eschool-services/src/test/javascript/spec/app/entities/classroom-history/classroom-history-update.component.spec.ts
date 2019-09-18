/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EschoolTestModule } from '../../../test.module';
import { ClassroomHistoryUpdateComponent } from 'app/entities/classroom-history/classroom-history-update.component';
import { ClassroomHistoryService } from 'app/entities/classroom-history/classroom-history.service';
import { ClassroomHistory } from 'app/shared/model/classroom-history.model';

describe('Component Tests', () => {
    describe('ClassroomHistory Management Update Component', () => {
        let comp: ClassroomHistoryUpdateComponent;
        let fixture: ComponentFixture<ClassroomHistoryUpdateComponent>;
        let service: ClassroomHistoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [ClassroomHistoryUpdateComponent]
            })
                .overrideTemplate(ClassroomHistoryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ClassroomHistoryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClassroomHistoryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ClassroomHistory(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.classroomHistory = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ClassroomHistory();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.classroomHistory = entity;
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
