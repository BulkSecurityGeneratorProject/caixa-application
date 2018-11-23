import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFuncionario } from 'app/shared/model/funcionario.model';
import { Principal } from 'app/core';
import { FuncionarioService } from './funcionario.service';

@Component({
    selector: 'jhi-funcionario',
    templateUrl: './funcionario.component.html'
})
export class FuncionarioComponent implements OnInit, OnDestroy {
    funcionarios: IFuncionario[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private funcionarioService: FuncionarioService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.funcionarioService.query().subscribe(
            (res: HttpResponse<IFuncionario[]>) => {
                this.funcionarios = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFuncionarios();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFuncionario) {
        return item.id;
    }

    registerChangeInFuncionarios() {
        this.eventSubscriber = this.eventManager.subscribe('funcionarioListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
