import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFuncionario } from 'app/shared/model/funcionario.model';

type EntityResponseType = HttpResponse<IFuncionario>;
type EntityArrayResponseType = HttpResponse<IFuncionario[]>;

@Injectable({ providedIn: 'root' })
export class FuncionarioService {
    public resourceUrl = SERVER_API_URL + 'api/funcionarios';

    constructor(private http: HttpClient) {}

    create(funcionario: IFuncionario): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(funcionario);
        return this.http
            .post<IFuncionario>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(funcionario: IFuncionario): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(funcionario);
        return this.http
            .put<IFuncionario>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IFuncionario>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IFuncionario[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(funcionario: IFuncionario): IFuncionario {
        const copy: IFuncionario = Object.assign({}, funcionario, {
            dataEntrada:
                funcionario.dataEntrada != null && funcionario.dataEntrada.isValid() ? funcionario.dataEntrada.format(DATE_FORMAT) : null,
            dataSaida: funcionario.dataSaida != null && funcionario.dataSaida.isValid() ? funcionario.dataSaida.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dataEntrada = res.body.dataEntrada != null ? moment(res.body.dataEntrada) : null;
            res.body.dataSaida = res.body.dataSaida != null ? moment(res.body.dataSaida) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((funcionario: IFuncionario) => {
                funcionario.dataEntrada = funcionario.dataEntrada != null ? moment(funcionario.dataEntrada) : null;
                funcionario.dataSaida = funcionario.dataSaida != null ? moment(funcionario.dataSaida) : null;
            });
        }
        return res;
    }
}
