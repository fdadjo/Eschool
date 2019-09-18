/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EschoolTestModule } from '../../../test.module';
import { LessonHistoryPreparationDeleteDialogComponent } from 'app/entities/lesson-history-preparation/lesson-history-preparation-delete-dialog.component';
import { LessonHistoryPreparationService } from 'app/entities/lesson-history-preparation/lesson-history-preparation.service';

describe('Component Tests', () => {
    describe('LessonHistoryPreparation Management Delete Component', () => {
        let comp: LessonHistoryPreparationDeleteDialogComponent;
        let fixture: ComponentFixture<LessonHistoryPreparationDeleteDialogComponent>;
        let service: LessonHistoryPreparationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [LessonHistoryPreparationDeleteDialogComponent]
            })
                .overrideTemplate(LessonHistoryPreparationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LessonHistoryPreparationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LessonHistoryPreparationService);
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
