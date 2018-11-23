import { Moment } from 'moment';
import { ITipoEntrada } from 'app/shared/model//tipo-entrada.model';
import { IPessoa } from 'app/shared/model//pessoa.model';

export interface IEntrada {
    id?: number;
    codEntrada?: number;
    valor?: number;
    data?: Moment;
    ano?: number;
    tipoEntrada?: ITipoEntrada;
    pessoa?: IPessoa;
}

export class Entrada implements IEntrada {
    constructor(
        public id?: number,
        public codEntrada?: number,
        public valor?: number,
        public data?: Moment,
        public ano?: number,
        public tipoEntrada?: ITipoEntrada,
        public pessoa?: IPessoa
    ) {}
}
