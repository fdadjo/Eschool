/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EschoolTestModule } from '../../../test.module';
import { TimeSlotHistoryComponent } from 'app/entities/time-slot-history/time-slot-history.component';
import { TimeSlotHistoryService } from 'app/entities/time-slot-history/time-slot-history.service';
import { TimeSlotHistory } from 'app/shared/model/time-slot-history.model';

describe('Component Tests', () => {
    describe('TimeSlotHistory Management Component', () => {
        let comp: TimeSlotHistoryComponent;
        let fixture: ComponentFixture<TimeSlotHistoryComponent>;
        let service: TimeSlotHistoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [TimeSlotHistoryComponent],
                providers: []
            })
                .overrideTemplate(TimeSlotHistoryComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TimeSlotHistoryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TimeSlotHistoryService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TimeSlotHistory(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.timeSlotHistories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
