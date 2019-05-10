import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'chaka-user',
        loadChildren: './chaka-user/chaka-user.module#ChakabackendChakaUserModule'
      },
      {
        path: 'transaction',
        loadChildren: './transaction/transaction.module#ChakabackendTransactionModule'
      },
      {
        path: 'user-account',
        loadChildren: './user-account/user-account.module#ChakabackendUserAccountModule'
      },
      {
        path: 'bonus',
        loadChildren: './bonus/bonus.module#ChakabackendBonusModule'
      },
      {
        path: 'invitation',
        loadChildren: './invitation/invitation.module#ChakabackendInvitationModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChakabackendEntityModule {}
