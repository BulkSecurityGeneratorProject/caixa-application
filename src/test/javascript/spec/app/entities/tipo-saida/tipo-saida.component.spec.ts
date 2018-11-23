/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CaixaTestModule } from '../../../test.module';
import { TipoSaidaComponent } from 'app/entities/tipo-saida/tipo-saida.component';
import { TipoSaidaService } from 'app/entities/tipo-saida/tipo-saida.service';
import { TipoSaida } from 'app/shared/model/tipo-saida.model';

describe('Component Tests', () => {
    describe('TipoSaida Management Component', () => {
        let comp: TipoSaidaComponent;
        let fixture: ComponentFixture<TipoSaidaComponent>;
        let service: TipoSaidaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CaixaTestModule],
                declarations: [TipoSaidaComponent],
                providers: []
            })
                .overrideTemplate(TipoSaidaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TipoSaidaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoSaidaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TipoSaida(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.tipoSaidas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
