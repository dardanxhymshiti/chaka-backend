import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChakabackendSharedModule } from 'app/shared';
import {
  InvitationComponent,
  InvitationDetailComponent,
  InvitationUpdateComponent,
  InvitationDeletePopupComponent,
  InvitationDeleteDialogComponent,
  invitationRoute,
  invitationPopupRoute
} from './';

const ENTITY_STATES = [...invitationRoute, ...invitationPopupRoute];

@NgModule({
  imports: [ChakabackendSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InvitationComponent,
    InvitationDetailComponent,
    InvitationUpdateComponent,
    InvitationDeleteDialogComponent,
    InvitationDeletePopupComponent
  ],
  entryComponents: [InvitationComponent, InvitationUpdateComponent, InvitationDeleteDialogComponent, InvitationDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChakabackendInvitationModule {}
