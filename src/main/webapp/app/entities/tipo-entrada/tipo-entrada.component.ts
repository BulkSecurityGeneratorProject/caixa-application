import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITipoEntrada } from 'app/shared/model/tipo-entrada.model';
import { Principal } from 'app/core';
import { TipoEntradaService } from './tipo-entrada.service';

@Component({
    selector: 'jhi-tipo-entrada',
    templateUrl: './tipo-entrada.component.html'
})
export class TipoEntradaComponent implements OnInit, OnDestroy {
    tipoEntradas: ITipoEntrada[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private tipoEntradaService: TipoEntradaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.tipoEntradaService.query().subscribe(
            (res: HttpResponse<ITipoEntrada[]>) => {
                this.tipoEntradas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTipoEntradas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITipoEntrada) {
        return item.id;
    }

    registerChangeInTipoEntradas() {
        this.eventSubscriber = this.eventManager.subscribe('tipoEntradaListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
