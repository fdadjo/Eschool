/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EschoolTestModule } from '../../../test.module';
import { TimeSlotHistoryDetailComponent } from 'app/entities/time-slot-history/time-slot-history-detail.component';
import { TimeSlotHistory } from 'app/shared/model/time-slot-history.model';

describe('Component Tests', () => {
    describe('TimeSlotHistory Management Detail Component', () => {
        let comp: TimeSlotHistoryDetailComponent;
        let fixture: ComponentFixture<TimeSlotHistoryDetailComponent>;
        const route = ({ data: of({ timeSlotHistory: new TimeSlotHistory(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [TimeSlotHistoryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TimeSlotHistoryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TimeSlotHistoryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.timeSlotHistory).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
