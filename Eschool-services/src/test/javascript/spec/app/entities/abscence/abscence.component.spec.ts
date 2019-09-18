/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EschoolTestModule } from '../../../test.module';
import { AbscenceComponent } from 'app/entities/abscence/abscence.component';
import { AbscenceService } from 'app/entities/abscence/abscence.service';
import { Abscence } from 'app/shared/model/abscence.model';

describe('Component Tests', () => {
    describe('Abscence Management Component', () => {
        let comp: AbscenceComponent;
        let fixture: ComponentFixture<AbscenceComponent>;
        let service: AbscenceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EschoolTestModule],
                declarations: [AbscenceComponent],
                providers: []
            })
                .overrideTemplate(AbscenceComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AbscenceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AbscenceService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Abscence(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.abscences[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
