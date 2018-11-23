import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CaixaSharedModule } from 'app/shared';
import {
    FuncionarioComponent,
    FuncionarioDetailComponent,
    FuncionarioUpdateComponent,
    FuncionarioDeletePopupComponent,
    FuncionarioDeleteDialogComponent,
    funcionarioRoute,
    funcionarioPopupRoute
} from './';

const ENTITY_STATES = [...funcionarioRoute, ...funcionarioPopupRoute];

@NgModule({
    imports: [CaixaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FuncionarioComponent,
        FuncionarioDetailComponent,
        FuncionarioUpdateComponent,
        FuncionarioDeleteDialogComponent,
        FuncionarioDeletePopupComponent
    ],
    entryComponents: [FuncionarioComponent, FuncionarioUpdateComponent, FuncionarioDeleteDialogComponent, FuncionarioDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CaixaFuncionarioModule {}
