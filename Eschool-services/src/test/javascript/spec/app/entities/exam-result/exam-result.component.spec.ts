/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EschoolTestModule } from '../../../test.module';
import { ExamResultComponent } from 'app/entities/exam-result/exam-result.component';
import { ExamResultService } from 'app/entities/exam-result/exam-result.service';
import { ExamResult } from 'app/shared/model/exam-result.model';

describe('Component Tests', () => {
    describe('ExamResult Management Component', () => {
        let comp: ExamResultComponent;
        let fixture: ComponentFixture<ExamResultComponent>;
        let service: ExamResultService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [ExamResultComponent],
                providers: []
            })
                .overrideTemplate(ExamResultComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExamResultComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExamResultService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ExamResult(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.examResults[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
