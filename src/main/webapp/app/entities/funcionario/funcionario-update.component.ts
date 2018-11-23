import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IFuncionario } from 'app/shared/model/funcionario.model';
import { FuncionarioService } from './funcionario.service';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from 'app/entities/pessoa';

@Component({
    selector: 'jhi-funcionario-update',
    templateUrl: './funcionario-update.component.html'
})
export class FuncionarioUpdateComponent implements OnInit {
    funcionario: IFuncionario;
    isSaving: boolean;

    pessoas: IPessoa[];
    dataEntradaDp: any;
    dataSaidaDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private funcionarioService: FuncionarioService,
        private pessoaService: PessoaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ funcionario }) => {
            this.funcionario = funcionario;
        });
        this.pessoaService.query({ filter: 'funcionario-is-null' }).subscribe(
            (res: HttpResponse<IPessoa[]>) => {
                if (!this.funcionario.pessoa || !this.funcionario.pessoa.id) {
                    this.pessoas = res.body;
                } else {
                    this.pessoaService.find(this.funcionario.pessoa.id).subscribe(
                        (subRes: HttpResponse<IPessoa>) => {
                            this.pessoas = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.funcionario.id !== undefined) {
            this.subscribeToSaveResponse(this.funcionarioService.update(this.funcionario));
        } else {
            this.subscribeToSaveResponse(this.funcionarioService.create(this.funcionario));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFuncionario>>) {
        result.subscribe((res: HttpResponse<IFuncionario>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPessoaById(index: number, item: IPessoa) {
        return item.id;
    }
}
