import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoSaida } from 'app/shared/model/tipo-saida.model';

@Component({
    selector: 'jhi-tipo-saida-detail',
    templateUrl: './tipo-saida-detail.component.html'
})
export class TipoSaidaDetailComponent implements OnInit {
    tipoSaida: ITipoSaida;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoSaida }) => {
            this.tipoSaida = tipoSaida;
        });
    }

    previousState() {
        window.history.back();
    }
}
