import { Moment } from 'moment';

export const enum Sexo {
    Masculino = 'Masculino',
    Feminino = 'Feminino'
}

export interface IPessoa {
    id?: number;
    codPessoa?: number;
    nome?: string;
    dataNasc?: Moment;
    sexo?: Sexo;
    cpf?: number;
}

export class Pessoa implements IPessoa {
    constructor(
        public id?: number,
        public codPessoa?: number,
        public nome?: string,
        public dataNasc?: Moment,
        public sexo?: Sexo,
        public cpf?: number
    ) {}
}
