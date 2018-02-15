/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EcoleTestModule } from '../../../test.module';
import { AdresseDetailComponent } from '../../../../../../main/webapp/app/entities/adresse/adresse-detail.component';
import { AdresseService } from '../../../../../../main/webapp/app/entities/adresse/adresse.service';
import { Adresse } from '../../../../../../main/webapp/app/entities/adresse/adresse.model';

describe('Component Tests', () => {

    describe('Adresse Management Detail Component', () => {
        let comp: AdresseDetailComponent;
        let fixture: ComponentFixture<AdresseDetailComponent>;
        let service: AdresseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EcoleTestModule],
                declarations: [AdresseDetailComponent],
                providers: [
                    AdresseService
                ]
            })
            .overrideTemplate(AdresseDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AdresseDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdresseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Adresse(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.adresse).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
