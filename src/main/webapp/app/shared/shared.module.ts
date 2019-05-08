import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ChakabackendSharedLibsModule, ChakabackendSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [ChakabackendSharedLibsModule, ChakabackendSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [ChakabackendSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChakabackendSharedModule {
  static forRoot() {
    return {
      ngModule: ChakabackendSharedModule
    };
  }
}
