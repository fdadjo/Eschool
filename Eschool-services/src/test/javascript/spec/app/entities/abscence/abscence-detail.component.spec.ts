/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EschoolTestModule } from '../../../test.module';
import { AbscenceDetailComponent } from 'app/entities/abscence/abscence-detail.component';
import { Abscence } from 'app/shared/model/abscence.model';

describe('Component Tests', () => {
    describe('Abscence Management Detail Component', () => {
        let comp: AbscenceDetailComponent;
        let fixture: ComponentFixture<AbscenceDetailComponent>;
        const route = ({ data: of({ abscence: new Abscence(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [AbscenceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AbscenceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AbscenceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.abscence).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
