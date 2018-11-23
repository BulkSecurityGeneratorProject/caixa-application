import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { ISaida } from 'app/shared/model/saida.model';
import { SaidaService } from './saida.service';
import { ITipoSaida } from 'app/shared/model/tipo-saida.model';
import { TipoSaidaService } from 'app/entities/tipo-saida';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from 'app/entities/pessoa';

@Component({
    selector: 'jhi-saida-update',
    templateUrl: './saida-update.component.html'
})
export class SaidaUpdateComponent implements OnInit {
    saida: ISaida;
    isSaving: boolean;

    tiposaidas: ITipoSaida[];

    pessoas: IPessoa[];
    dataDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private saidaService: SaidaService,
        private tipoSaidaService: TipoSaidaService,
        private pessoaService: PessoaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ saida }) => {
            this.saida = saida;
        });
        this.tipoSaidaService.query().subscribe(
            (res: HttpResponse<ITipoSaida[]>) => {
                this.tiposaidas = res.body;
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
        if (this.saida.id !== undefined) {
            this.subscribeToSaveResponse(this.saidaService.update(this.saida));
        } else {
            this.subscribeToSaveResponse(this.saidaService.create(this.saida));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISaida>>) {
        result.subscribe((res: HttpResponse<ISaida>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTipoSaidaById(index: number, item: ITipoSaida) {
        return item.id;
    }

    trackPessoaById(index: number, item: IPessoa) {
        return item.id;
    }
}
