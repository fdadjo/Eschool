/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EschoolTestModule } from '../../../test.module';
import { LessonHistoryDeleteDialogComponent } from 'app/entities/lesson-history/lesson-history-delete-dialog.component';
import { LessonHistoryService } from 'app/entities/lesson-history/lesson-history.service';

describe('Component Tests', () => {
    describe('LessonHistory Management Delete Component', () => {
        let comp: LessonHistoryDeleteDialogComponent;
        let fixture: ComponentFixture<LessonHistoryDeleteDialogComponent>;
        let service: LessonHistoryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [LessonHistoryDeleteDialogComponent]
            })
                .overrideTemplate(LessonHistoryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LessonHistoryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LessonHistoryService);
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
