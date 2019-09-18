/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EschoolTestModule } from '../../../test.module';
import { StudentHomeworkComponent } from 'app/entities/student-homework/student-homework.component';
import { StudentHomeworkService } from 'app/entities/student-homework/student-homework.service';
import { StudentHomework } from 'app/shared/model/student-homework.model';

describe('Component Tests', () => {
    describe('StudentHomework Management Component', () => {
        let comp: StudentHomeworkComponent;
        let fixture: ComponentFixture<StudentHomeworkComponent>;
        let service: StudentHomeworkService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [StudentHomeworkComponent],
                providers: []
            })
                .overrideTemplate(StudentHomeworkComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StudentHomeworkComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentHomeworkService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new StudentHomework(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.studentHomeworks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
