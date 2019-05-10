import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInvitation } from 'app/shared/model/invitation.model';
import { InvitationService } from './invitation.service';

@Component({
  selector: 'jhi-invitation-delete-dialog',
  templateUrl: './invitation-delete-dialog.component.html'
})
export class InvitationDeleteDialogComponent {
  invitation: IInvitation;

  constructor(
    protected invitationService: InvitationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.invitationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'invitationListModification',
        content: 'Deleted an invitation'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-invitation-delete-popup',
  template: ''
})
export class InvitationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ invitation }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InvitationDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.invitation = invitation;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/invitation', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/invitation', { outlets: { popup: null } }]);
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
