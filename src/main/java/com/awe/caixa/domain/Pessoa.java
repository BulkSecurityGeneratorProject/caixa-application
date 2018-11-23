package com.awe.caixa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.awe.caixa.domain.enumeration.Sexo;

/**
 * A Pessoa.
 */
@Entity
@Table(name = "pessoa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cod_pessoa")
    private Integer codPessoa;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "data_nasc", nullable = false)
    private LocalDate dataNasc;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private Sexo sexo;

    @NotNull
    @Column(name = "cpf", nullable = false)
    private Integer cpf;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodPessoa() {
        return codPessoa;
    }

    public Pessoa codPessoa(Integer codPessoa) {
        this.codPessoa = codPessoa;
        return this;
    }

    public void setCodPessoa(Integer codPessoa) {
        this.codPessoa = codPessoa;
    }

    public String getNome() {
        return nome;
    }

    public Pessoa nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public Pessoa dataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
        return this;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public Pessoa sexo(Sexo sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Integer getCpf() {
        return cpf;
    }

    public Pessoa cpf(Integer cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(Integer cpf) {
        this.cpf = cpf;
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
        Pessoa pessoa = (Pessoa) o;
        if (pessoa.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pessoa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pessoa{" +
            "id=" + getId() +
            ", codPessoa=" + getCodPessoa() +
            ", nome='" + getNome() + "'" +
            ", dataNasc='" + getDataNasc() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", cpf=" + getCpf() +
            "}";
    }
}
