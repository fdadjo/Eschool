/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EschoolTestModule } from '../../../test.module';
import { HomeworkDeleteDialogComponent } from 'app/entities/homework/homework-delete-dialog.component';
import { HomeworkService } from 'app/entities/homework/homework.service';

describe('Component Tests', () => {
    describe('Homework Management Delete Component', () => {
        let comp: HomeworkDeleteDialogComponent;
        let fixture: ComponentFixture<HomeworkDeleteDialogComponent>;
        let service: HomeworkService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [HomeworkDeleteDialogComponent]
            })
                .overrideTemplate(HomeworkDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HomeworkDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HomeworkService);
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
