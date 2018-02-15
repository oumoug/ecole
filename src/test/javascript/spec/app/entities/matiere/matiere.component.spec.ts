/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoleTestModule } from '../../../test.module';
import { MatiereComponent } from '../../../../../../main/webapp/app/entities/matiere/matiere.component';
import { MatiereService } from '../../../../../../main/webapp/app/entities/matiere/matiere.service';
import { Matiere } from '../../../../../../main/webapp/app/entities/matiere/matiere.model';

describe('Component Tests', () => {

    describe('Matiere Management Component', () => {
        let comp: MatiereComponent;
        let fixture: ComponentFixture<MatiereComponent>;
        let service: MatiereService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EcoleTestModule],
                declarations: [MatiereComponent],
                providers: [
                    MatiereService
                ]
            })
            .overrideTemplate(MatiereComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MatiereComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MatiereService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Matiere(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.matieres[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
