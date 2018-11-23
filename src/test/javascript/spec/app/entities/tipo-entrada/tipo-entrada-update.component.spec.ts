/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CaixaTestModule } from '../../../test.module';
import { TipoEntradaUpdateComponent } from 'app/entities/tipo-entrada/tipo-entrada-update.component';
import { TipoEntradaService } from 'app/entities/tipo-entrada/tipo-entrada.service';
import { TipoEntrada } from 'app/shared/model/tipo-entrada.model';

describe('Component Tests', () => {
    describe('TipoEntrada Management Update Component', () => {
        let comp: TipoEntradaUpdateComponent;
        let fixture: ComponentFixture<TipoEntradaUpdateComponent>;
        let service: TipoEntradaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CaixaTestModule],
                declarations: [TipoEntradaUpdateComponent]
            })
                .overrideTemplate(TipoEntradaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TipoEntradaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoEntradaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TipoEntrada(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tipoEntrada = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TipoEntrada();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tipoEntrada = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
