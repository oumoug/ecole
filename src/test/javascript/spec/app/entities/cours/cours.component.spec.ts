/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EcoleTestModule } from '../../../test.module';
import { CoursComponent } from '../../../../../../main/webapp/app/entities/cours/cours.component';
import { CoursService } from '../../../../../../main/webapp/app/entities/cours/cours.service';
import { Cours } from '../../../../../../main/webapp/app/entities/cours/cours.model';

describe('Component Tests', () => {

    describe('Cours Management Component', () => {
        let comp: CoursComponent;
        let fixture: ComponentFixture<CoursComponent>;
        let service: CoursService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EcoleTestModule],
                declarations: [CoursComponent],
                providers: [
                    CoursService
                ]
            })
            .overrideTemplate(CoursComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CoursComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoursService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Cours(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.cours[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
