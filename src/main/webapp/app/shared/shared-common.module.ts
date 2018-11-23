import { NgModule } from '@angular/core';

import { CaixaSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [CaixaSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [CaixaSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class CaixaSharedCommonModule {}
