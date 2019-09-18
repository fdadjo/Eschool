import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITimeSheet } from 'app/shared/model/time-sheet.model';
import { TimeSheetService } from './time-sheet.service';

@Component({
    selector: 'jhi-time-sheet-delete-dialog',
    templateUrl: './time-sheet-delete-dialog.component.html'
})
export class TimeSheetDeleteDialogComponent {
    timeSheet: ITimeSheet;

    constructor(
        protected timeSheetService: TimeSheetService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.timeSheetService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'timeSheetListModification',
                content: 'Deleted an timeSheet'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-time-sheet-delete-popup',
    template: ''
})
export class TimeSheetDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ timeSheet }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TimeSheetDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.timeSheet = timeSheet;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/time-sheet', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/time-sheet', { outlets: { popup: null } }]);
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
