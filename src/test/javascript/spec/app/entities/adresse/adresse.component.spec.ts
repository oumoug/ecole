/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoleTestModule } from '../../../test.module';
import { AdresseComponent } from '../../../../../../main/webapp/app/entities/adresse/adresse.component';
import { AdresseService } from '../../../../../../main/webapp/app/entities/adresse/adresse.service';
import { Adresse } from '../../../../../../main/webapp/app/entities/adresse/adresse.model';

describe('Component Tests', () => {

    describe('Adresse Management Component', () => {
        let comp: AdresseComponent;
        let fixture: ComponentFixture<AdresseComponent>;
        let service: AdresseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EcoleTestModule],
                declarations: [AdresseComponent],
                providers: [
                    AdresseService
                ]
            })
            .overrideTemplate(AdresseComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AdresseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdresseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Adresse(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.adresses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
