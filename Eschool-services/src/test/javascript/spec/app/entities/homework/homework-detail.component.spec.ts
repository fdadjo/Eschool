/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EschoolTestModule } from '../../../test.module';
import { HomeworkDetailComponent } from 'app/entities/homework/homework-detail.component';
import { Homework } from 'app/shared/model/homework.model';

describe('Component Tests', () => {
    describe('Homework Management Detail Component', () => {
        let comp: HomeworkDetailComponent;
        let fixture: ComponentFixture<HomeworkDetailComponent>;
        const route = ({ data: of({ homework: new Homework(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [HomeworkDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HomeworkDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HomeworkDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.homework).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
