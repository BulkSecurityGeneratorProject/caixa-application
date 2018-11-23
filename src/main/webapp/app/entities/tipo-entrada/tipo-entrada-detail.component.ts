import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoEntrada } from 'app/shared/model/tipo-entrada.model';

@Component({
    selector: 'jhi-tipo-entrada-detail',
    templateUrl: './tipo-entrada-detail.component.html'
})
export class TipoEntradaDetailComponent implements OnInit {
    tipoEntrada: ITipoEntrada;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoEntrada }) => {
            this.tipoEntrada = tipoEntrada;
        });
    }

    previousState() {
        window.history.back();
    }
}
