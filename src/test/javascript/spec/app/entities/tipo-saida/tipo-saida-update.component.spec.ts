/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CaixaTestModule } from '../../../test.module';
import { TipoSaidaUpdateComponent } from 'app/entities/tipo-saida/tipo-saida-update.component';
import { TipoSaidaService } from 'app/entities/tipo-saida/tipo-saida.service';
import { TipoSaida } from 'app/shared/model/tipo-saida.model';

describe('Component Tests', () => {
    describe('TipoSaida Management Update Component', () => {
        let comp: TipoSaidaUpdateComponent;
        let fixture: ComponentFixture<TipoSaidaUpdateComponent>;
        let service: TipoSaidaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CaixaTestModule],
                declarations: [TipoSaidaUpdateComponent]
            })
                .overrideTemplate(TipoSaidaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TipoSaidaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoSaidaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TipoSaida(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tipoSaida = entity;
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
                    const entity = new TipoSaida();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tipoSaida = entity;
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
