/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EcoleTestModule } from '../../../test.module';
import { ProfesseurDialogComponent } from '../../../../../../main/webapp/app/entities/professeur/professeur-dialog.component';
import { ProfesseurService } from '../../../../../../main/webapp/app/entities/professeur/professeur.service';
import { Professeur } from '../../../../../../main/webapp/app/entities/professeur/professeur.model';
import { AdresseService } from '../../../../../../main/webapp/app/entities/adresse';
import { ClasseService } from '../../../../../../main/webapp/app/entities/classe';
import { MatiereService } from '../../../../../../main/webapp/app/entities/matiere';

describe('Component Tests', () => {

    describe('Professeur Management Dialog Component', () => {
        let comp: ProfesseurDialogComponent;
        let fixture: ComponentFixture<ProfesseurDialogComponent>;
        let service: ProfesseurService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EcoleTestModule],
                declarations: [ProfesseurDialogComponent],
                providers: [
                    AdresseService,
                    ClasseService,
                    MatiereService,
                    ProfesseurService
                ]
            })
            .overrideTemplate(ProfesseurDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProfesseurDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProfesseurService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Professeur(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.professeur = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'professeurListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Professeur();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.professeur = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'professeurListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
