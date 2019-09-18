import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExamResult } from 'app/shared/model/exam-result.model';
import { ExamResultService } from './exam-result.service';

@Component({
    selector: 'jhi-exam-result-delete-dialog',
    templateUrl: './exam-result-delete-dialog.component.html'
})
export class ExamResultDeleteDialogComponent {
    examResult: IExamResult;

    constructor(
        protected examResultService: ExamResultService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.examResultService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'examResultListModification',
                content: 'Deleted an examResult'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-exam-result-delete-popup',
    template: ''
})
export class ExamResultDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ examResult }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ExamResultDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.examResult = examResult;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/exam-result', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/exam-result', { outlets: { popup: null } }]);
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
