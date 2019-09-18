import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITimeSlotHistory } from 'app/shared/model/time-slot-history.model';
import { TimeSlotHistoryService } from './time-slot-history.service';

@Component({
    selector: 'jhi-time-slot-history-delete-dialog',
    templateUrl: './time-slot-history-delete-dialog.component.html'
})
export class TimeSlotHistoryDeleteDialogComponent {
    timeSlotHistory: ITimeSlotHistory;

    constructor(
        protected timeSlotHistoryService: TimeSlotHistoryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.timeSlotHistoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'timeSlotHistoryListModification',
                content: 'Deleted an timeSlotHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-time-slot-history-delete-popup',
    template: ''
})
export class TimeSlotHistoryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ timeSlotHistory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TimeSlotHistoryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.timeSlotHistory = timeSlotHistory;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/time-slot-history', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/time-slot-history', { outlets: { popup: null } }]);
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
