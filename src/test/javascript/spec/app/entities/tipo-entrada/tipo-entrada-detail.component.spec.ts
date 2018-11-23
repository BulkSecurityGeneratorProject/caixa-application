/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CaixaTestModule } from '../../../test.module';
import { TipoEntradaDetailComponent } from 'app/entities/tipo-entrada/tipo-entrada-detail.component';
import { TipoEntrada } from 'app/shared/model/tipo-entrada.model';

describe('Component Tests', () => {
    describe('TipoEntrada Management Detail Component', () => {
        let comp: TipoEntradaDetailComponent;
        let fixture: ComponentFixture<TipoEntradaDetailComponent>;
        const route = ({ data: of({ tipoEntrada: new TipoEntrada(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CaixaTestModule],
                declarations: [TipoEntradaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TipoEntradaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoEntradaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tipoEntrada).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
