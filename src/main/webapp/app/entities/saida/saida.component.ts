import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISaida } from 'app/shared/model/saida.model';
import { Principal } from 'app/core';
import { SaidaService } from './saida.service';

@Component({
    selector: 'jhi-saida',
    templateUrl: './saida.component.html'
})
export class SaidaComponent implements OnInit, OnDestroy {
    saidas: ISaida[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private saidaService: SaidaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.saidaService.query().subscribe(
            (res: HttpResponse<ISaida[]>) => {
                this.saidas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSaidas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISaida) {
        return item.id;
    }

    registerChangeInSaidas() {
        this.eventSubscriber = this.eventManager.subscribe('saidaListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
