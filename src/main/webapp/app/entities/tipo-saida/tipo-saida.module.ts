import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CaixaSharedModule } from 'app/shared';
import {
    TipoSaidaComponent,
    TipoSaidaDetailComponent,
    TipoSaidaUpdateComponent,
    TipoSaidaDeletePopupComponent,
    TipoSaidaDeleteDialogComponent,
    tipoSaidaRoute,
    tipoSaidaPopupRoute
} from './';

const ENTITY_STATES = [...tipoSaidaRoute, ...tipoSaidaPopupRoute];

@NgModule({
    imports: [CaixaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TipoSaidaComponent,
        TipoSaidaDetailComponent,
        TipoSaidaUpdateComponent,
        TipoSaidaDeleteDialogComponent,
        TipoSaidaDeletePopupComponent
    ],
    entryComponents: [TipoSaidaComponent, TipoSaidaUpdateComponent, TipoSaidaDeleteDialogComponent, TipoSaidaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CaixaTipoSaidaModule {}
