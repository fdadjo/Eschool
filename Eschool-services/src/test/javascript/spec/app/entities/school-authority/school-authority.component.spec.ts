/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EschoolTestModule } from '../../../test.module';
import { SchoolAuthorityComponent } from 'app/entities/school-authority/school-authority.component';
import { SchoolAuthorityService } from 'app/entities/school-authority/school-authority.service';
import { SchoolAuthority } from 'app/shared/model/school-authority.model';

describe('Component Tests', () => {
    describe('SchoolAuthority Management Component', () => {
        let comp: SchoolAuthorityComponent;
        let fixture: ComponentFixture<SchoolAuthorityComponent>;
        let service: SchoolAuthorityService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [SchoolAuthorityComponent],
                providers: []
            })
                .overrideTemplate(SchoolAuthorityComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SchoolAuthorityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SchoolAuthorityService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SchoolAuthority(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.schoolAuthorities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
