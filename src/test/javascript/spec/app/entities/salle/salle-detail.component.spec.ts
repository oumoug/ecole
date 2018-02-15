/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EcoleTestModule } from '../../../test.module';
import { SalleDetailComponent } from '../../../../../../main/webapp/app/entities/salle/salle-detail.component';
import { SalleService } from '../../../../../../main/webapp/app/entities/salle/salle.service';
import { Salle } from '../../../../../../main/webapp/app/entities/salle/salle.model';

describe('Component Tests', () => {

    describe('Salle Management Detail Component', () => {
        let comp: SalleDetailComponent;
        let fixture: ComponentFixture<SalleDetailComponent>;
        let service: SalleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EcoleTestModule],
                declarations: [SalleDetailComponent],
                providers: [
                    SalleService
                ]
            })
            .overrideTemplate(SalleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SalleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SalleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Salle(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.salle).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
