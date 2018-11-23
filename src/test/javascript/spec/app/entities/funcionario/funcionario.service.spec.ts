/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { FuncionarioService } from 'app/entities/funcionario/funcionario.service';
import { IFuncionario, Funcionario } from 'app/shared/model/funcionario.model';

describe('Service Tests', () => {
    describe('Funcionario Service', () => {
        let injector: TestBed;
        let service: FuncionarioService;
        let httpMock: HttpTestingController;
        let elemDefault: IFuncionario;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(FuncionarioService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Funcionario(0, 0, 0, currentDate, currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dataEntrada: currentDate.format(DATE_FORMAT),
                        dataSaida: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Funcionario', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dataEntrada: currentDate.format(DATE_FORMAT),
                        dataSaida: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dataEntrada: currentDate,
                        dataSaida: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Funcionario(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Funcionario', async () => {
                const returnedFromService = Object.assign(
                    {
                        codFuncionario: 1,
                        carteiraTrabalho: 1,
                        dataEntrada: currentDate.format(DATE_FORMAT),
                        dataSaida: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dataEntrada: currentDate,
                        dataSaida: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Funcionario', async () => {
                const returnedFromService = Object.assign(
                    {
                        codFuncionario: 1,
                        carteiraTrabalho: 1,
                        dataEntrada: currentDate.format(DATE_FORMAT),
                        dataSaida: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dataEntrada: currentDate,
                        dataSaida: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Funcionario', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
