import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Cours } from './cours.model';
import { CoursService } from './cours.service';

@Injectable()
export class CoursPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private coursService: CoursService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.coursService.find(id)
                    .subscribe((coursResponse: HttpResponse<Cours>) => {
                        const cours: Cours = coursResponse.body;
                        if (cours.date) {
                            cours.date = {
                                year: cours.date.getFullYear(),
                                month: cours.date.getMonth() + 1,
                                day: cours.date.getDate()
                            };
                        }
                        cours.heure = this.datePipe
                            .transform(cours.heure, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.coursModalRef(component, cours);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.coursModalRef(component, new Cours());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    coursModalRef(component: Component, cours: Cours): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.cours = cours;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
