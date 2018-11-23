import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoEntrada } from 'app/shared/model/tipo-entrada.model';

type EntityResponseType = HttpResponse<ITipoEntrada>;
type EntityArrayResponseType = HttpResponse<ITipoEntrada[]>;

@Injectable({ providedIn: 'root' })
export class TipoEntradaService {
    public resourceUrl = SERVER_API_URL + 'api/tipo-entradas';

    constructor(private http: HttpClient) {}

    create(tipoEntrada: ITipoEntrada): Observable<EntityResponseType> {
        return this.http.post<ITipoEntrada>(this.resourceUrl, tipoEntrada, { observe: 'response' });
    }

    update(tipoEntrada: ITipoEntrada): Observable<EntityResponseType> {
        return this.http.put<ITipoEntrada>(this.resourceUrl, tipoEntrada, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITipoEntrada>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITipoEntrada[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
