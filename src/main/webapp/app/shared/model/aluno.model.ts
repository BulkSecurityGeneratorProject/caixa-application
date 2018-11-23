import { Moment } from 'moment';
import { IPessoa } from 'app/shared/model//pessoa.model';

export interface IAluno {
    id?: number;
    codAluno?: number;
    dataEntrada?: Moment;
    dataSaida?: Moment;
    pessoa?: IPessoa;
}

export class Aluno implements IAluno {
    constructor(
        public id?: number,
        public codAluno?: number,
        public dataEntrada?: Moment,
        public dataSaida?: Moment,
        public pessoa?: IPessoa
    ) {}
}
