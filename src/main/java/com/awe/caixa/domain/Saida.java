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
 * A Saida.
 */
@Entity
@Table(name = "saida")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Saida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cod_saida")
    private Integer codSaida;

    @NotNull
    @Column(name = "valor", nullable = false)
    private Double valor;

    @Column(name = "data")
    private LocalDate data;

    @Column(name = "ano")
    private Integer ano;

    @ManyToOne
    @JsonIgnoreProperties("")
    private TipoSaida tipoSaida;

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

    public Integer getCodSaida() {
        return codSaida;
    }

    public Saida codSaida(Integer codSaida) {
        this.codSaida = codSaida;
        return this;
    }

    public void setCodSaida(Integer codSaida) {
        this.codSaida = codSaida;
    }

    public Double getValor() {
        return valor;
    }

    public Saida valor(Double valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public Saida data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Integer getAno() {
        return ano;
    }

    public Saida ano(Integer ano) {
        this.ano = ano;
        return this;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public TipoSaida getTipoSaida() {
        return tipoSaida;
    }

    public Saida tipoSaida(TipoSaida tipoSaida) {
        this.tipoSaida = tipoSaida;
        return this;
    }

    public void setTipoSaida(TipoSaida tipoSaida) {
        this.tipoSaida = tipoSaida;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Saida pessoa(Pessoa pessoa) {
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
        Saida saida = (Saida) o;
        if (saida.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), saida.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Saida{" +
            "id=" + getId() +
            ", codSaida=" + getCodSaida() +
            ", valor=" + getValor() +
            ", data='" + getData() + "'" +
            ", ano=" + getAno() +
            "}";
    }
}
