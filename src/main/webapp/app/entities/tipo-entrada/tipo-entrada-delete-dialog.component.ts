import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoEntrada } from 'app/shared/model/tipo-entrada.model';
import { TipoEntradaService } from './tipo-entrada.service';

@Component({
    selector: 'jhi-tipo-entrada-delete-dialog',
    templateUrl: './tipo-entrada-delete-dialog.component.html'
})
export class TipoEntradaDeleteDialogComponent {
    tipoEntrada: ITipoEntrada;

    constructor(
        private tipoEntradaService: TipoEntradaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipoEntradaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tipoEntradaListModification',
                content: 'Deleted an tipoEntrada'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tipo-entrada-delete-popup',
    template: ''
})
export class TipoEntradaDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoEntrada }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TipoEntradaDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.tipoEntrada = tipoEntrada;
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
