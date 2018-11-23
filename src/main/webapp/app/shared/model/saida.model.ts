import { Moment } from 'moment';
import { ITipoSaida } from 'app/shared/model//tipo-saida.model';
import { IPessoa } from 'app/shared/model//pessoa.model';

export interface ISaida {
    id?: number;
    codSaida?: number;
    valor?: number;
    data?: Moment;
    ano?: number;
    tipoSaida?: ITipoSaida;
    pessoa?: IPessoa;
}

export class Saida implements ISaida {
    constructor(
        public id?: number,
        public codSaida?: number,
        public valor?: number,
        public data?: Moment,
        public ano?: number,
        public tipoSaida?: ITipoSaida,
        public pessoa?: IPessoa
    ) {}
}
