/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EschoolTestModule } from '../../../test.module';
import { ClassroomHistoryComponent } from 'app/entities/classroom-history/classroom-history.component';
import { ClassroomHistoryService } from 'app/entities/classroom-history/classroom-history.service';
import { ClassroomHistory } from 'app/shared/model/classroom-history.model';

describe('Component Tests', () => {
    describe('ClassroomHistory Management Component', () => {
        let comp: ClassroomHistoryComponent;
        let fixture: ComponentFixture<ClassroomHistoryComponent>;
        let service: ClassroomHistoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [ClassroomHistoryComponent],
                providers: []
            })
                .overrideTemplate(ClassroomHistoryComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ClassroomHistoryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClassroomHistoryService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ClassroomHistory(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.classroomHistories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
