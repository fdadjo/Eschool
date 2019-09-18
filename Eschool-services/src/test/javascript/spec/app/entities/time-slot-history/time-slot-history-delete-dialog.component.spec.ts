/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EschoolTestModule } from '../../../test.module';
import { TimeSlotHistoryDeleteDialogComponent } from 'app/entities/time-slot-history/time-slot-history-delete-dialog.component';
import { TimeSlotHistoryService } from 'app/entities/time-slot-history/time-slot-history.service';

describe('Component Tests', () => {
    describe('TimeSlotHistory Management Delete Component', () => {
        let comp: TimeSlotHistoryDeleteDialogComponent;
        let fixture: ComponentFixture<TimeSlotHistoryDeleteDialogComponent>;
        let service: TimeSlotHistoryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [TimeSlotHistoryDeleteDialogComponent]
            })
                .overrideTemplate(TimeSlotHistoryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TimeSlotHistoryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TimeSlotHistoryService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
