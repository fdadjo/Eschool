/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EschoolTestModule } from '../../../test.module';
import { JobHistoryComponent } from 'app/entities/job-history/job-history.component';
import { JobHistoryService } from 'app/entities/job-history/job-history.service';
import { JobHistory } from 'app/shared/model/job-history.model';

describe('Component Tests', () => {
    describe('JobHistory Management Component', () => {
        let comp: JobHistoryComponent;
        let fixture: ComponentFixture<JobHistoryComponent>;
        let service: JobHistoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [JobHistoryComponent],
                providers: []
            })
                .overrideTemplate(JobHistoryComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(JobHistoryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobHistoryService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new JobHistory(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.jobHistories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
