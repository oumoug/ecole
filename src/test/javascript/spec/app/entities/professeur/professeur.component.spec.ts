/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoleTestModule } from '../../../test.module';
import { ProfesseurComponent } from '../../../../../../main/webapp/app/entities/professeur/professeur.component';
import { ProfesseurService } from '../../../../../../main/webapp/app/entities/professeur/professeur.service';
import { Professeur } from '../../../../../../main/webapp/app/entities/professeur/professeur.model';

describe('Component Tests', () => {

    describe('Professeur Management Component', () => {
        let comp: ProfesseurComponent;
        let fixture: ComponentFixture<ProfesseurComponent>;
        let service: ProfesseurService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EcoleTestModule],
                declarations: [ProfesseurComponent],
                providers: [
                    ProfesseurService
                ]
            })
            .overrideTemplate(ProfesseurComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProfesseurComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProfesseurService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Professeur(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.professeurs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
