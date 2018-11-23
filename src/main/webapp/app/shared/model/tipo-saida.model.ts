export interface ITipoSaida {
    id?: number;
    codTSaida?: number;
    descTSaida?: string;
}

export class TipoSaida implements ITipoSaida {
    constructor(public id?: number, public codTSaida?: number, public descTSaida?: string) {}
}
