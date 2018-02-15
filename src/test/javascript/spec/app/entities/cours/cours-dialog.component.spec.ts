/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EcoleTestModule } from '../../../test.module';
import { CoursDialogComponent } from '../../../../../../main/webapp/app/entities/cours/cours-dialog.component';
import { CoursService } from '../../../../../../main/webapp/app/entities/cours/cours.service';
import { Cours } from '../../../../../../main/webapp/app/entities/cours/cours.model';
import { ProfesseurService } from '../../../../../../main/webapp/app/entities/professeur';
import { ClasseService } from '../../../../../../main/webapp/app/entities/classe';
import { SalleService } from '../../../../../../main/webapp/app/entities/salle';

describe('Component Tests', () => {

    describe('Cours Management Dialog Component', () => {
        let comp: CoursDialogComponent;
        let fixture: ComponentFixture<CoursDialogComponent>;
        let service: CoursService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EcoleTestModule],
                declarations: [CoursDialogComponent],
                providers: [
                    ProfesseurService,
                    ClasseService,
                    SalleService,
                    CoursService
                ]
            })
            .overrideTemplate(CoursDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CoursDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoursService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Cours(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.cours = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'coursListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Cours();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.cours = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'coursListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
