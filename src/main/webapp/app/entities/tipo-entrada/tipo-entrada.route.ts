import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TipoEntrada } from 'app/shared/model/tipo-entrada.model';
import { TipoEntradaService } from './tipo-entrada.service';
import { TipoEntradaComponent } from './tipo-entrada.component';
import { TipoEntradaDetailComponent } from './tipo-entrada-detail.component';
import { TipoEntradaUpdateComponent } from './tipo-entrada-update.component';
import { TipoEntradaDeletePopupComponent } from './tipo-entrada-delete-dialog.component';
import { ITipoEntrada } from 'app/shared/model/tipo-entrada.model';

@Injectable({ providedIn: 'root' })
export class TipoEntradaResolve implements Resolve<ITipoEntrada> {
    constructor(private service: TipoEntradaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TipoEntrada> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TipoEntrada>) => response.ok),
                map((tipoEntrada: HttpResponse<TipoEntrada>) => tipoEntrada.body)
            );
        }
        return of(new TipoEntrada());
    }
}

export const tipoEntradaRoute: Routes = [
    {
        path: 'tipo-entrada',
        component: TipoEntradaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoEntradas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-entrada/:id/view',
        component: TipoEntradaDetailComponent,
        resolve: {
            tipoEntrada: TipoEntradaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoEntradas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-entrada/new',
        component: TipoEntradaUpdateComponent,
        resolve: {
            tipoEntrada: TipoEntradaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoEntradas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-entrada/:id/edit',
        component: TipoEntradaUpdateComponent,
        resolve: {
            tipoEntrada: TipoEntradaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoEntradas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoEntradaPopupRoute: Routes = [
    {
        path: 'tipo-entrada/:id/delete',
        component: TipoEntradaDeletePopupComponent,
        resolve: {
            tipoEntrada: TipoEntradaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TipoEntradas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
