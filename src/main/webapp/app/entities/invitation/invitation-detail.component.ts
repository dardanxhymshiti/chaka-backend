import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInvitation } from 'app/shared/model/invitation.model';

@Component({
  selector: 'jhi-invitation-detail',
  templateUrl: './invitation-detail.component.html'
})
export class InvitationDetailComponent implements OnInit {
  invitation: IInvitation;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ invitation }) => {
      this.invitation = invitation;
    });
  }

  previousState() {
    window.history.back();
  }
}
