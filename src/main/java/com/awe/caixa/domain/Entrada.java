package com.awe.caixa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Entrada.
 */
@Entity
@Table(name = "entrada")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Entrada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cod_entrada")
    private Integer codEntrada;

    @NotNull
    @Column(name = "valor", nullable = false)
    private Double valor;

    @Column(name = "data")
    private LocalDate data;

    @Column(name = "ano")
    private Integer ano;

    @ManyToOne
    @JsonIgnoreProperties("")
    private TipoEntrada tipoEntrada;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Pessoa pessoa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodEntrada() {
        return codEntrada;
    }

    public Entrada codEntrada(Integer codEntrada) {
        this.codEntrada = codEntrada;
        return this;
    }

    public void setCodEntrada(Integer codEntrada) {
        this.codEntrada = codEntrada;
    }

    public Double getValor() {
        return valor;
    }

    public Entrada valor(Double valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public Entrada data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Integer getAno() {
        return ano;
    }

    public Entrada ano(Integer ano) {
        this.ano = ano;
        return this;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public TipoEntrada getTipoEntrada() {
        return tipoEntrada;
    }

    public Entrada tipoEntrada(TipoEntrada tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
        return this;
    }

    public void setTipoEntrada(TipoEntrada tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Entrada pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
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
        Entrada entrada = (Entrada) o;
        if (entrada.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entrada.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Entrada{" +
            "id=" + getId() +
            ", codEntrada=" + getCodEntrada() +
            ", valor=" + getValor() +
            ", data='" + getData() + "'" +
            ", ano=" + getAno() +
            "}";
    }
}
