/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EcoleTestModule } from '../../../test.module';
import { ProfesseurDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/professeur/professeur-delete-dialog.component';
import { ProfesseurService } from '../../../../../../main/webapp/app/entities/professeur/professeur.service';

describe('Component Tests', () => {

    describe('Professeur Management Delete Component', () => {
        let comp: ProfesseurDeleteDialogComponent;
        let fixture: ComponentFixture<ProfesseurDeleteDialogComponent>;
        let service: ProfesseurService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EcoleTestModule],
                declarations: [ProfesseurDeleteDialogComponent],
                providers: [
                    ProfesseurService
                ]
            })
            .overrideTemplate(ProfesseurDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProfesseurDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProfesseurService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
