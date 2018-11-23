/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AlunoService } from 'app/entities/aluno/aluno.service';
import { IAluno, Aluno } from 'app/shared/model/aluno.model';

describe('Service Tests', () => {
    describe('Aluno Service', () => {
        let injector: TestBed;
        let service: AlunoService;
        let httpMock: HttpTestingController;
        let elemDefault: IAluno;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(AlunoService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Aluno(0, 0, currentDate, currentDate);
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

            it('should create a Aluno', async () => {
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
                    .create(new Aluno(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Aluno', async () => {
                const returnedFromService = Object.assign(
                    {
                        codAluno: 1,
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

            it('should return a list of Aluno', async () => {
                const returnedFromService = Object.assign(
                    {
                        codAluno: 1,
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

            it('should delete a Aluno', async () => {
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
