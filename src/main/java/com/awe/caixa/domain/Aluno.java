package com.awe.caixa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Aluno.
 */
@Entity
@Table(name = "aluno")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Aluno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cod_aluno")
    private Integer codAluno;

    @Column(name = "data_entrada")
    private LocalDate dataEntrada;

    @Column(name = "data_saida")
    private LocalDate dataSaida;

    @OneToOne    @JoinColumn(unique = true)
    private Pessoa pessoa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodAluno() {
        return codAluno;
    }

    public Aluno codAluno(Integer codAluno) {
        this.codAluno = codAluno;
        return this;
    }

    public void setCodAluno(Integer codAluno) {
        this.codAluno = codAluno;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public Aluno dataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
        return this;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public Aluno dataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
        return this;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Aluno pessoa(Pessoa pessoa) {
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
        Aluno aluno = (Aluno) o;
        if (aluno.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aluno.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Aluno{" +
            "id=" + getId() +
            ", codAluno=" + getCodAluno() +
            ", dataEntrada='" + getDataEntrada() + "'" +
            ", dataSaida='" + getDataSaida() + "'" +
            "}";
    }
}
