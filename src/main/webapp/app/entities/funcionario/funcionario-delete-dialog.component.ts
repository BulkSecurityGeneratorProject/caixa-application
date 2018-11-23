import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFuncionario } from 'app/shared/model/funcionario.model';
import { FuncionarioService } from './funcionario.service';

@Component({
    selector: 'jhi-funcionario-delete-dialog',
    templateUrl: './funcionario-delete-dialog.component.html'
})
export class FuncionarioDeleteDialogComponent {
    funcionario: IFuncionario;

    constructor(
        private funcionarioService: FuncionarioService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.funcionarioService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'funcionarioListModification',
                content: 'Deleted an funcionario'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-funcionario-delete-popup',
    template: ''
})
export class FuncionarioDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ funcionario }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FuncionarioDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.funcionario = funcionario;
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
