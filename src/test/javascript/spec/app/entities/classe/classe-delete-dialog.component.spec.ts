/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EcoleTestModule } from '../../../test.module';
import { ClasseDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/classe/classe-delete-dialog.component';
import { ClasseService } from '../../../../../../main/webapp/app/entities/classe/classe.service';

describe('Component Tests', () => {

    describe('Classe Management Delete Component', () => {
        let comp: ClasseDeleteDialogComponent;
        let fixture: ComponentFixture<ClasseDeleteDialogComponent>;
        let service: ClasseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EcoleTestModule],
                declarations: [ClasseDeleteDialogComponent],
                providers: [
                    ClasseService
                ]
            })
            .overrideTemplate(ClasseDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClasseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClasseService);
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
