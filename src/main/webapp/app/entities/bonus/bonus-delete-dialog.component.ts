import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBonus } from 'app/shared/model/bonus.model';
import { BonusService } from './bonus.service';

@Component({
  selector: 'jhi-bonus-delete-dialog',
  templateUrl: './bonus-delete-dialog.component.html'
})
export class BonusDeleteDialogComponent {
  bonus: IBonus;

  constructor(protected bonusService: BonusService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.bonusService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'bonusListModification',
        content: 'Deleted an bonus'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-bonus-delete-popup',
  template: ''
})
export class BonusDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ bonus }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BonusDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.bonus = bonus;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/bonus', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/bonus', { outlets: { popup: null } }]);
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
