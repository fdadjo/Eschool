/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EschoolTestModule } from '../../../test.module';
import { SchoolAuthorityUpdateComponent } from 'app/entities/school-authority/school-authority-update.component';
import { SchoolAuthorityService } from 'app/entities/school-authority/school-authority.service';
import { SchoolAuthority } from 'app/shared/model/school-authority.model';

describe('Component Tests', () => {
    describe('SchoolAuthority Management Update Component', () => {
        let comp: SchoolAuthorityUpdateComponent;
        let fixture: ComponentFixture<SchoolAuthorityUpdateComponent>;
        let service: SchoolAuthorityService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [SchoolAuthorityUpdateComponent]
            })
                .overrideTemplate(SchoolAuthorityUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SchoolAuthorityUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SchoolAuthorityService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SchoolAuthority(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.schoolAuthority = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SchoolAuthority();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.schoolAuthority = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
