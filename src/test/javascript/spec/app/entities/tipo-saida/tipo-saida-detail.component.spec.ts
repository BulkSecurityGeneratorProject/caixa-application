/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CaixaTestModule } from '../../../test.module';
import { TipoSaidaDetailComponent } from 'app/entities/tipo-saida/tipo-saida-detail.component';
import { TipoSaida } from 'app/shared/model/tipo-saida.model';

describe('Component Tests', () => {
    describe('TipoSaida Management Detail Component', () => {
        let comp: TipoSaidaDetailComponent;
        let fixture: ComponentFixture<TipoSaidaDetailComponent>;
        const route = ({ data: of({ tipoSaida: new TipoSaida(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CaixaTestModule],
                declarations: [TipoSaidaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TipoSaidaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoSaidaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tipoSaida).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
