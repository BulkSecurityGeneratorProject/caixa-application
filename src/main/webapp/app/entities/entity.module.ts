import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CaixaTipoEntradaModule } from './tipo-entrada/tipo-entrada.module';
import { CaixaEntradaModule } from './entrada/entrada.module';
import { CaixaTipoSaidaModule } from './tipo-saida/tipo-saida.module';
import { CaixaSaidaModule } from './saida/saida.module';
import { CaixaPessoaModule } from './pessoa/pessoa.module';
import { CaixaAlunoModule } from './aluno/aluno.module';
import { CaixaFuncionarioModule } from './funcionario/funcionario.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        CaixaTipoEntradaModule,
        CaixaEntradaModule,
        CaixaTipoSaidaModule,
        CaixaSaidaModule,
        CaixaPessoaModule,
        CaixaAlunoModule,
        CaixaFuncionarioModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CaixaEntityModule {}
