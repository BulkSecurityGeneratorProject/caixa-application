import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoSaida } from 'app/shared/model/tipo-saida.model';
import { TipoSaidaService } from './tipo-saida.service';

@Component({
    selector: 'jhi-tipo-saida-delete-dialog',
    templateUrl: './tipo-saida-delete-dialog.component.html'
})
export class TipoSaidaDeleteDialogComponent {
    tipoSaida: ITipoSaida;

    constructor(private tipoSaidaService: TipoSaidaService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipoSaidaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tipoSaidaListModification',
                content: 'Deleted an tipoSaida'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tipo-saida-delete-popup',
    template: ''
})
export class TipoSaidaDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoSaida }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TipoSaidaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.tipoSaida = tipoSaida;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
