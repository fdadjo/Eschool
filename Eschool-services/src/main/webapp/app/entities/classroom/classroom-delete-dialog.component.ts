import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClassroom } from 'app/shared/model/classroom.model';
import { ClassroomService } from './classroom.service';

@Component({
    selector: 'jhi-classroom-delete-dialog',
    templateUrl: './classroom-delete-dialog.component.html'
})
export class ClassroomDeleteDialogComponent {
    classroom: IClassroom;

    constructor(
        protected classroomService: ClassroomService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.classroomService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'classroomListModification',
                content: 'Deleted an classroom'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-classroom-delete-popup',
    template: ''
})
export class ClassroomDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ classroom }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ClassroomDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.classroom = classroom;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/classroom', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/classroom', { outlets: { popup: null } }]);
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
