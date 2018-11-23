package com.awe.caixa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Funcionario.
 */
@Entity
@Table(name = "funcionario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Funcionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cod_funcionario")
    private Integer codFuncionario;

    @Column(name = "carteira_trabalho")
    private Integer carteiraTrabalho;

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

    public Integer getCodFuncionario() {
        return codFuncionario;
    }

    public Funcionario codFuncionario(Integer codFuncionario) {
        this.codFuncionario = codFuncionario;
        return this;
    }

    public void setCodFuncionario(Integer codFuncionario) {
        this.codFuncionario = codFuncionario;
    }

    public Integer getCarteiraTrabalho() {
        return carteiraTrabalho;
    }

    public Funcionario carteiraTrabalho(Integer carteiraTrabalho) {
        this.carteiraTrabalho = carteiraTrabalho;
        return this;
    }

    public void setCarteiraTrabalho(Integer carteiraTrabalho) {
        this.carteiraTrabalho = carteiraTrabalho;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public Funcionario dataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
        return this;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public Funcionario dataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
        return this;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Funcionario pessoa(Pessoa pessoa) {
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
        Funcionario funcionario = (Funcionario) o;
        if (funcionario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), funcionario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Funcionario{" +
            "id=" + getId() +
            ", codFuncionario=" + getCodFuncionario() +
            ", carteiraTrabalho=" + getCarteiraTrabalho() +
            ", dataEntrada='" + getDataEntrada() + "'" +
            ", dataSaida='" + getDataSaida() + "'" +
            "}";
    }
}
