import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPessoa } from 'app/shared/model/pessoa.model';
import { Principal } from 'app/core';
import { PessoaService } from './pessoa.service';

@Component({
    selector: 'jhi-pessoa',
    templateUrl: './pessoa.component.html'
})
export class PessoaComponent implements OnInit, OnDestroy {
    pessoas: IPessoa[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private pessoaService: PessoaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.pessoaService.query().subscribe(
            (res: HttpResponse<IPessoa[]>) => {
                this.pessoas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPessoas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPessoa) {
        return item.id;
    }

    registerChangeInPessoas() {
        this.eventSubscriber = this.eventManager.subscribe('pessoaListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
