import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CaixaSharedModule } from 'app/shared';
import {
    TipoEntradaComponent,
    TipoEntradaDetailComponent,
    TipoEntradaUpdateComponent,
    TipoEntradaDeletePopupComponent,
    TipoEntradaDeleteDialogComponent,
    tipoEntradaRoute,
    tipoEntradaPopupRoute
} from './';

const ENTITY_STATES = [...tipoEntradaRoute, ...tipoEntradaPopupRoute];

@NgModule({
    imports: [CaixaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TipoEntradaComponent,
        TipoEntradaDetailComponent,
        TipoEntradaUpdateComponent,
        TipoEntradaDeleteDialogComponent,
        TipoEntradaDeletePopupComponent
    ],
    entryComponents: [TipoEntradaComponent, TipoEntradaUpdateComponent, TipoEntradaDeleteDialogComponent, TipoEntradaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CaixaTipoEntradaModule {}
