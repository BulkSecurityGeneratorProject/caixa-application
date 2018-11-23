export interface ITipoEntrada {
    id?: number;
    codTEntrada?: number;
    descTEntrada?: string;
}

export class TipoEntrada implements ITipoEntrada {
    constructor(public id?: number, public codTEntrada?: number, public descTEntrada?: string) {}
}
