package com.awe.caixa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TipoSaida.
 */
@Entity
@Table(name = "tipo_saida")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoSaida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cod_t_saida")
    private Integer codTSaida;

    @NotNull
    @Column(name = "desc_t_saida", nullable = false)
    private String descTSaida;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodTSaida() {
        return codTSaida;
    }

    public TipoSaida codTSaida(Integer codTSaida) {
        this.codTSaida = codTSaida;
        return this;
    }

    public void setCodTSaida(Integer codTSaida) {
        this.codTSaida = codTSaida;
    }

    public String getDescTSaida() {
        return descTSaida;
    }

    public TipoSaida descTSaida(String descTSaida) {
        this.descTSaida = descTSaida;
        return this;
    }

    public void setDescTSaida(String descTSaida) {
        this.descTSaida = descTSaida;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TipoSaida tipoSaida = (TipoSaida) o;
        if (tipoSaida.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoSaida.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoSaida{" +
            "id=" + getId() +
            ", codTSaida=" + getCodTSaida() +
            ", descTSaida='" + getDescTSaida() + "'" +
            "}";
    }
}
