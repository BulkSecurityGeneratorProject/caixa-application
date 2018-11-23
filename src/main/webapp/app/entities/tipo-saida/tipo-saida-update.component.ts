import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITipoSaida } from 'app/shared/model/tipo-saida.model';
import { TipoSaidaService } from './tipo-saida.service';

@Component({
    selector: 'jhi-tipo-saida-update',
    templateUrl: './tipo-saida-update.component.html'
})
export class TipoSaidaUpdateComponent implements OnInit {
    tipoSaida: ITipoSaida;
    isSaving: boolean;

    constructor(private tipoSaidaService: TipoSaidaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tipoSaida }) => {
            this.tipoSaida = tipoSaida;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tipoSaida.id !== undefined) {
            this.subscribeToSaveResponse(this.tipoSaidaService.update(this.tipoSaida));
        } else {
            this.subscribeToSaveResponse(this.tipoSaidaService.create(this.tipoSaida));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITipoSaida>>) {
        result.subscribe((res: HttpResponse<ITipoSaida>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
