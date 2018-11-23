/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CaixaTestModule } from '../../../test.module';
import { TipoEntradaDeleteDialogComponent } from 'app/entities/tipo-entrada/tipo-entrada-delete-dialog.component';
import { TipoEntradaService } from 'app/entities/tipo-entrada/tipo-entrada.service';

describe('Component Tests', () => {
    describe('TipoEntrada Management Delete Component', () => {
        let comp: TipoEntradaDeleteDialogComponent;
        let fixture: ComponentFixture<TipoEntradaDeleteDialogComponent>;
        let service: TipoEntradaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CaixaTestModule],
                declarations: [TipoEntradaDeleteDialogComponent]
            })
                .overrideTemplate(TipoEntradaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoEntradaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoEntradaService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
