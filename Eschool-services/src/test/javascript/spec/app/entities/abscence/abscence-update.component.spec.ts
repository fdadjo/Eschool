/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EschoolTestModule } from '../../../test.module';
import { AbscenceUpdateComponent } from 'app/entities/abscence/abscence-update.component';
import { AbscenceService } from 'app/entities/abscence/abscence.service';
import { Abscence } from 'app/shared/model/abscence.model';

describe('Component Tests', () => {
    describe('Abscence Management Update Component', () => {
        let comp: AbscenceUpdateComponent;
        let fixture: ComponentFixture<AbscenceUpdateComponent>;
        let service: AbscenceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [AbscenceUpdateComponent]
            })
                .overrideTemplate(AbscenceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AbscenceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AbscenceService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Abscence(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.abscence = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Abscence();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.abscence = entity;
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
