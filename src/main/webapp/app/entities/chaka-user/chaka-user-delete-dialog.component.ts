import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChakaUser } from 'app/shared/model/chaka-user.model';
import { ChakaUserService } from './chaka-user.service';

@Component({
  selector: 'jhi-chaka-user-delete-dialog',
  templateUrl: './chaka-user-delete-dialog.component.html'
})
export class ChakaUserDeleteDialogComponent {
  chakaUser: IChakaUser;

  constructor(protected chakaUserService: ChakaUserService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.chakaUserService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'chakaUserListModification',
        content: 'Deleted an chakaUser'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-chaka-user-delete-popup',
  template: ''
})
export class ChakaUserDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ chakaUser }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ChakaUserDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.chakaUser = chakaUser;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/chaka-user', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/chaka-user', { outlets: { popup: null } }]);
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
