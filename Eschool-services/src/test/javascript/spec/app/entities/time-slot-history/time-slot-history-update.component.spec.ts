/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EschoolTestModule } from '../../../test.module';
import { TimeSlotHistoryUpdateComponent } from 'app/entities/time-slot-history/time-slot-history-update.component';
import { TimeSlotHistoryService } from 'app/entities/time-slot-history/time-slot-history.service';
import { TimeSlotHistory } from 'app/shared/model/time-slot-history.model';

describe('Component Tests', () => {
    describe('TimeSlotHistory Management Update Component', () => {
        let comp: TimeSlotHistoryUpdateComponent;
        let fixture: ComponentFixture<TimeSlotHistoryUpdateComponent>;
        let service: TimeSlotHistoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [TimeSlotHistoryUpdateComponent]
            })
                .overrideTemplate(TimeSlotHistoryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TimeSlotHistoryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TimeSlotHistoryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TimeSlotHistory(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.timeSlotHistory = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TimeSlotHistory();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.timeSlotHistory = entity;
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
