import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISchoolAuthority } from 'app/shared/model/school-authority.model';
import { SchoolAuthorityService } from './school-authority.service';

@Component({
    selector: 'jhi-school-authority-delete-dialog',
    templateUrl: './school-authority-delete-dialog.component.html'
})
export class SchoolAuthorityDeleteDialogComponent {
    schoolAuthority: ISchoolAuthority;

    constructor(
        protected schoolAuthorityService: SchoolAuthorityService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.schoolAuthorityService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'schoolAuthorityListModification',
                content: 'Deleted an schoolAuthority'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-school-authority-delete-popup',
    template: ''
})
export class SchoolAuthorityDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ schoolAuthority }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SchoolAuthorityDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.schoolAuthority = schoolAuthority;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/school-authority', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/school-authority', { outlets: { popup: null } }]);
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
