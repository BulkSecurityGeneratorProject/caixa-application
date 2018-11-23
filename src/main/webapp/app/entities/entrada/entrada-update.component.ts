import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IEntrada } from 'app/shared/model/entrada.model';
import { EntradaService } from './entrada.service';
import { ITipoEntrada } from 'app/shared/model/tipo-entrada.model';
import { TipoEntradaService } from 'app/entities/tipo-entrada';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from 'app/entities/pessoa';

@Component({
    selector: 'jhi-entrada-update',
    templateUrl: './entrada-update.component.html'
})
export class EntradaUpdateComponent implements OnInit {
    entrada: IEntrada;
    isSaving: boolean;

    tipoentradas: ITipoEntrada[];

    pessoas: IPessoa[];
    dataDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private entradaService: EntradaService,
        private tipoEntradaService: TipoEntradaService,
        private pessoaService: PessoaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ entrada }) => {
            this.entrada = entrada;
        });
        this.tipoEntradaService.query().subscribe(
            (res: HttpResponse<ITipoEntrada[]>) => {
                this.tipoentradas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.pessoaService.query().subscribe(
            (res: HttpResponse<IPessoa[]>) => {
                this.pessoas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.entrada.id !== undefined) {
            this.subscribeToSaveResponse(this.entradaService.update(this.entrada));
        } else {
            this.subscribeToSaveResponse(this.entradaService.create(this.entrada));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEntrada>>) {
        result.subscribe((res: HttpResponse<IEntrada>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTipoEntradaById(index: number, item: ITipoEntrada) {
        return item.id;
    }

    trackPessoaById(index: number, item: IPessoa) {
        return item.id;
    }
}
