import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILessonHistoryPreparation } from 'app/shared/model/lesson-history-preparation.model';
import { LessonHistoryPreparationService } from './lesson-history-preparation.service';

@Component({
    selector: 'jhi-lesson-history-preparation-delete-dialog',
    templateUrl: './lesson-history-preparation-delete-dialog.component.html'
})
export class LessonHistoryPreparationDeleteDialogComponent {
    lessonHistoryPreparation: ILessonHistoryPreparation;

    constructor(
        protected lessonHistoryPreparationService: LessonHistoryPreparationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lessonHistoryPreparationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'lessonHistoryPreparationListModification',
                content: 'Deleted an lessonHistoryPreparation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lesson-history-preparation-delete-popup',
    template: ''
})
export class LessonHistoryPreparationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lessonHistoryPreparation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LessonHistoryPreparationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.lessonHistoryPreparation = lessonHistoryPreparation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/lesson-history-preparation', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/lesson-history-preparation', { outlets: { popup: null } }]);
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
