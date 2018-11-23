import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITipoEntrada } from 'app/shared/model/tipo-entrada.model';
import { TipoEntradaService } from './tipo-entrada.service';

@Component({
    selector: 'jhi-tipo-entrada-update',
    templateUrl: './tipo-entrada-update.component.html'
})
export class TipoEntradaUpdateComponent implements OnInit {
    tipoEntrada: ITipoEntrada;
    isSaving: boolean;

    constructor(private tipoEntradaService: TipoEntradaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tipoEntrada }) => {
            this.tipoEntrada = tipoEntrada;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tipoEntrada.id !== undefined) {
            this.subscribeToSaveResponse(this.tipoEntradaService.update(this.tipoEntrada));
        } else {
            this.subscribeToSaveResponse(this.tipoEntradaService.create(this.tipoEntrada));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITipoEntrada>>) {
        result.subscribe((res: HttpResponse<ITipoEntrada>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
