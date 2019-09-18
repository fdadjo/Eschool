import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAbscence } from 'app/shared/model/abscence.model';
import { AbscenceService } from './abscence.service';

@Component({
    selector: 'jhi-abscence-delete-dialog',
    templateUrl: './abscence-delete-dialog.component.html'
})
export class AbscenceDeleteDialogComponent {
    abscence: IAbscence;

    constructor(protected abscenceService: AbscenceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.abscenceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'abscenceListModification',
                content: 'Deleted an abscence'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-abscence-delete-popup',
    template: ''
})
export class AbscenceDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ abscence }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AbscenceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.abscence = abscence;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/abscence', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/abscence', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
