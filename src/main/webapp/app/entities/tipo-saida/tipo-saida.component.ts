import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITipoSaida } from 'app/shared/model/tipo-saida.model';
import { Principal } from 'app/core';
import { TipoSaidaService } from './tipo-saida.service';

@Component({
    selector: 'jhi-tipo-saida',
    templateUrl: './tipo-saida.component.html'
})
export class TipoSaidaComponent implements OnInit, OnDestroy {
    tipoSaidas: ITipoSaida[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private tipoSaidaService: TipoSaidaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.tipoSaidaService.query().subscribe(
            (res: HttpResponse<ITipoSaida[]>) => {
                this.tipoSaidas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTipoSaidas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITipoSaida) {
        return item.id;
    }

    registerChangeInTipoSaidas() {
        this.eventSubscriber = this.eventManager.subscribe('tipoSaidaListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
