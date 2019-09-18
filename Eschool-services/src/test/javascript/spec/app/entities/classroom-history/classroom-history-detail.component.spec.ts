/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EschoolTestModule } from '../../../test.module';
import { ClassroomHistoryDetailComponent } from 'app/entities/classroom-history/classroom-history-detail.component';
import { ClassroomHistory } from 'app/shared/model/classroom-history.model';

describe('Component Tests', () => {
    describe('ClassroomHistory Management Detail Component', () => {
        let comp: ClassroomHistoryDetailComponent;
        let fixture: ComponentFixture<ClassroomHistoryDetailComponent>;
        const route = ({ data: of({ classroomHistory: new ClassroomHistory(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [ClassroomHistoryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ClassroomHistoryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClassroomHistoryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.classroomHistory).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
