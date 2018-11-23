import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAluno } from 'app/shared/model/aluno.model';

type EntityResponseType = HttpResponse<IAluno>;
type EntityArrayResponseType = HttpResponse<IAluno[]>;

@Injectable({ providedIn: 'root' })
export class AlunoService {
    public resourceUrl = SERVER_API_URL + 'api/alunos';

    constructor(private http: HttpClient) {}

    create(aluno: IAluno): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(aluno);
        return this.http
            .post<IAluno>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(aluno: IAluno): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(aluno);
        return this.http
            .put<IAluno>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAluno>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAluno[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(aluno: IAluno): IAluno {
        const copy: IAluno = Object.assign({}, aluno, {
            dataEntrada: aluno.dataEntrada != null && aluno.dataEntrada.isValid() ? aluno.dataEntrada.format(DATE_FORMAT) : null,
            dataSaida: aluno.dataSaida != null && aluno.dataSaida.isValid() ? aluno.dataSaida.format(DATE_FORMAT) : null
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
            res.body.forEach((aluno: IAluno) => {
                aluno.dataEntrada = aluno.dataEntrada != null ? moment(aluno.dataEntrada) : null;
                aluno.dataSaida = aluno.dataSaida != null ? moment(aluno.dataSaida) : null;
            });
        }
        return res;
    }
}
