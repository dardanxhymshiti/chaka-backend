import { NgModule } from '@angular/core';

import { ChakabackendSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [ChakabackendSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [ChakabackendSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class ChakabackendSharedCommonModule {}
