/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EschoolTestModule } from '../../../test.module';
import { HomeworkComponent } from 'app/entities/homework/homework.component';
import { HomeworkService } from 'app/entities/homework/homework.service';
import { Homework } from 'app/shared/model/homework.model';

describe('Component Tests', () => {
    describe('Homework Management Component', () => {
        let comp: HomeworkComponent;
        let fixture: ComponentFixture<HomeworkComponent>;
        let service: HomeworkService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [HomeworkComponent],
                providers: []
            })
                .overrideTemplate(HomeworkComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HomeworkComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HomeworkService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Homework(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.homework[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
