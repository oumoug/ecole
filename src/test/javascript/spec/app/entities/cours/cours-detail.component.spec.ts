/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EcoleTestModule } from '../../../test.module';
import { CoursDetailComponent } from '../../../../../../main/webapp/app/entities/cours/cours-detail.component';
import { CoursService } from '../../../../../../main/webapp/app/entities/cours/cours.service';
import { Cours } from '../../../../../../main/webapp/app/entities/cours/cours.model';

describe('Component Tests', () => {

    describe('Cours Management Detail Component', () => {
        let comp: CoursDetailComponent;
        let fixture: ComponentFixture<CoursDetailComponent>;
        let service: CoursService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EcoleTestModule],
                declarations: [CoursDetailComponent],
                providers: [
                    CoursService
                ]
            })
            .overrideTemplate(CoursDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CoursDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoursService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Cours(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.cours).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
