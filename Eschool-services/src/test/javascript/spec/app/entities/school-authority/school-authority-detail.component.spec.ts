/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EschoolTestModule } from '../../../test.module';
import { SchoolAuthorityDetailComponent } from 'app/entities/school-authority/school-authority-detail.component';
import { SchoolAuthority } from 'app/shared/model/school-authority.model';

describe('Component Tests', () => {
    describe('SchoolAuthority Management Detail Component', () => {
        let comp: SchoolAuthorityDetailComponent;
        let fixture: ComponentFixture<SchoolAuthorityDetailComponent>;
        const route = ({ data: of({ schoolAuthority: new SchoolAuthority(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [SchoolAuthorityDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SchoolAuthorityDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SchoolAuthorityDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.schoolAuthority).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
