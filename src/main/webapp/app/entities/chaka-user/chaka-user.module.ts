import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChakabackendSharedModule } from 'app/shared';
import {
  ChakaUserComponent,
  ChakaUserDetailComponent,
  ChakaUserUpdateComponent,
  ChakaUserDeletePopupComponent,
  ChakaUserDeleteDialogComponent,
  chakaUserRoute,
  chakaUserPopupRoute
} from './';

const ENTITY_STATES = [...chakaUserRoute, ...chakaUserPopupRoute];

@NgModule({
  imports: [ChakabackendSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ChakaUserComponent,
    ChakaUserDetailComponent,
    ChakaUserUpdateComponent,
    ChakaUserDeleteDialogComponent,
    ChakaUserDeletePopupComponent
  ],
  entryComponents: [ChakaUserComponent, ChakaUserUpdateComponent, ChakaUserDeleteDialogComponent, ChakaUserDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChakabackendChakaUserModule {}
