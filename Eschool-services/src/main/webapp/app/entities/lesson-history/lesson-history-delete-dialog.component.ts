import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILessonHistory } from 'app/shared/model/lesson-history.model';
import { LessonHistoryService } from './lesson-history.service';

@Component({
    selector: 'jhi-lesson-history-delete-dialog',
    templateUrl: './lesson-history-delete-dialog.component.html'
})
export class LessonHistoryDeleteDialogComponent {
    lessonHistory: ILessonHistory;

    constructor(
        protected lessonHistoryService: LessonHistoryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lessonHistoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'lessonHistoryListModification',
                content: 'Deleted an lessonHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lesson-history-delete-popup',
    template: ''
})
export class LessonHistoryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lessonHistory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LessonHistoryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.lessonHistory = lessonHistory;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/lesson-history', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/lesson-history', { outlets: { popup: null } }]);
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
