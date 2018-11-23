package com.awe.caixa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TipoEntrada.
 */
@Entity
@Table(name = "tipo_entrada")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoEntrada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cod_t_entrada")
    private Integer codTEntrada;

    @NotNull
    @Column(name = "desc_t_entrada", nullable = false)
    private String descTEntrada;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodTEntrada() {
        return codTEntrada;
    }

    public TipoEntrada codTEntrada(Integer codTEntrada) {
        this.codTEntrada = codTEntrada;
        return this;
    }

    public void setCodTEntrada(Integer codTEntrada) {
        this.codTEntrada = codTEntrada;
    }

    public String getDescTEntrada() {
        return descTEntrada;
    }

    public TipoEntrada descTEntrada(String descTEntrada) {
        this.descTEntrada = descTEntrada;
        return this;
    }

    public void setDescTEntrada(String descTEntrada) {
        this.descTEntrada = descTEntrada;
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
        TipoEntrada tipoEntrada = (TipoEntrada) o;
        if (tipoEntrada.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoEntrada.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoEntrada{" +
            "id=" + getId() +
            ", codTEntrada=" + getCodTEntrada() +
            ", descTEntrada='" + getDescTEntrada() + "'" +
            "}";
    }
}
