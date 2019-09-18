import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClassroomHistory } from 'app/shared/model/classroom-history.model';
import { ClassroomHistoryService } from './classroom-history.service';

@Component({
    selector: 'jhi-classroom-history-delete-dialog',
    templateUrl: './classroom-history-delete-dialog.component.html'
})
export class ClassroomHistoryDeleteDialogComponent {
    classroomHistory: IClassroomHistory;

    constructor(
        protected classroomHistoryService: ClassroomHistoryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.classroomHistoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'classroomHistoryListModification',
                content: 'Deleted an classroomHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-classroom-history-delete-popup',
    template: ''
})
export class ClassroomHistoryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ classroomHistory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ClassroomHistoryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.classroomHistory = classroomHistory;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/classroom-history', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/classroom-history', { outlets: { popup: null } }]);
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
