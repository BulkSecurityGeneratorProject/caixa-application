/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CaixaTestModule } from '../../../test.module';
import { TipoSaidaDeleteDialogComponent } from 'app/entities/tipo-saida/tipo-saida-delete-dialog.component';
import { TipoSaidaService } from 'app/entities/tipo-saida/tipo-saida.service';

describe('Component Tests', () => {
    describe('TipoSaida Management Delete Component', () => {
        let comp: TipoSaidaDeleteDialogComponent;
        let fixture: ComponentFixture<TipoSaidaDeleteDialogComponent>;
        let service: TipoSaidaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CaixaTestModule],
                declarations: [TipoSaidaDeleteDialogComponent]
            })
                .overrideTemplate(TipoSaidaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoSaidaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoSaidaService);
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