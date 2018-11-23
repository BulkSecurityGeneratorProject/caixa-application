/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CaixaTestModule } from '../../../test.module';
import { TipoEntradaComponent } from 'app/entities/tipo-entrada/tipo-entrada.component';
import { TipoEntradaService } from 'app/entities/tipo-entrada/tipo-entrada.service';
import { TipoEntrada } from 'app/shared/model/tipo-entrada.model';

describe('Component Tests', () => {
    describe('TipoEntrada Management Component', () => {
        let comp: TipoEntradaComponent;
        let fixture: ComponentFixture<TipoEntradaComponent>;
        let service: TipoEntradaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CaixaTestModule],
                declarations: [TipoEntradaComponent],
                providers: []
            })
                .overrideTemplate(TipoEntradaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TipoEntradaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoEntradaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TipoEntrada(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.tipoEntradas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
