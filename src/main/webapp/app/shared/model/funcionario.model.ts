import { Moment } from 'moment';
import { IPessoa } from 'app/shared/model//pessoa.model';

export interface IFuncionario {
    id?: number;
    codFuncionario?: number;
    carteiraTrabalho?: number;
    dataEntrada?: Moment;
    dataSaida?: Moment;
    pessoa?: IPessoa;
}

export class Funcionario implements IFuncionario {
    constructor(
        public id?: number,
        public codFuncionario?: number,
        public carteiraTrabalho?: number,
        public dataEntrada?: Moment,
        public dataSaida?: Moment,
        public pessoa?: IPessoa
    ) {}
}
