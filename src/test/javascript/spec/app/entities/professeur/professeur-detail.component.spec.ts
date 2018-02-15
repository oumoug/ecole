/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EcoleTestModule } from '../../../test.module';
import { ProfesseurDetailComponent } from '../../../../../../main/webapp/app/entities/professeur/professeur-detail.component';
import { ProfesseurService } from '../../../../../../main/webapp/app/entities/professeur/professeur.service';
import { Professeur } from '../../../../../../main/webapp/app/entities/professeur/professeur.model';

describe('Component Tests', () => {

    describe('Professeur Management Detail Component', () => {
        let comp: ProfesseurDetailComponent;
        let fixture: ComponentFixture<ProfesseurDetailComponent>;
        let service: ProfesseurService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EcoleTestModule],
                declarations: [ProfesseurDetailComponent],
                providers: [
                    ProfesseurService
                ]
            })
            .overrideTemplate(ProfesseurDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProfesseurDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProfesseurService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Professeur(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.professeur).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
