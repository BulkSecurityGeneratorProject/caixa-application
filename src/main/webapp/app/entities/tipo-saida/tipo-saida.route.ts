import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TipoSaida } from 'app/shared/model/tipo-saida.model';
import { TipoSaidaService } from './tipo-saida.service';
import { TipoSaidaComponent } from './tipo-saida.component';
import { TipoSaidaDetailComponent } from './tipo-saida-detail.component';
import { TipoSaidaUpdateComponent } from './tipo-saida-update.component';
import { TipoSaidaDeletePopupComponent } from './tipo-saida-delete-dialog.component';
import { ITipoSaida } from 'app/shared/model/tipo-saida.model';

@Injectable({ providedIn: 'root' })
export class TipoSaidaResolve implements Resolve<ITipoSaida> {
    constructor(private service: TipoSaidaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TipoSaida> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TipoSaida>) => response.ok),
                map((tipoSaida: HttpResponse<TipoSaida>) => tipoSaida.body)
            );
        }
        return of(new TipoSaida());
    }
}

export const tipoSaidaRoute: Routes = [
    {
        path: 'tipo-saida',
        component: TipoSaidaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoSaidas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-saida/:id/view',
        component: TipoSaidaDetailComponent,
        resolve: {
            tipoSaida: TipoSaidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoSaidas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-saida/new',
        component: TipoSaidaUpdateComponent,
        resolve: {
            tipoSaida: TipoSaidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoSaidas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-saida/:id/edit',
        component: TipoSaidaUpdateComponent,
        resolve: {
            tipoSaida: TipoSaidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoSaidas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoSaidaPopupRoute: Routes = [
    {
        path: 'tipo-saida/:id/delete',
        component: TipoSaidaDeletePopupComponent,
        resolve: {
            tipoSaida: TipoSaidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoSaidas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
