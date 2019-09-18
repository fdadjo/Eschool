/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EschoolTestModule } from '../../../test.module';
import { ClassroomHistoryDeleteDialogComponent } from 'app/entities/classroom-history/classroom-history-delete-dialog.component';
import { ClassroomHistoryService } from 'app/entities/classroom-history/classroom-history.service';

describe('Component Tests', () => {
    describe('ClassroomHistory Management Delete Component', () => {
        let comp: ClassroomHistoryDeleteDialogComponent;
        let fixture: ComponentFixture<ClassroomHistoryDeleteDialogComponent>;
        let service: ClassroomHistoryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [ClassroomHistoryDeleteDialogComponent]
            })
                .overrideTemplate(ClassroomHistoryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClassroomHistoryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClassroomHistoryService);
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
