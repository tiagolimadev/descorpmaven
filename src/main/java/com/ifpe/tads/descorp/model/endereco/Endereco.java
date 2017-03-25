/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.model.endereco;

import com.ifpe.tads.descorp.model.fornecedor.Fornecedor;
import com.ifpe.tads.descorp.model.usuario.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author Tiago Lima
 */
@Entity
@Table(name = "TB_ENDERECO")
public class Endereco implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TXT_LOGRADOURO")
    private String logradouro;
    @Column(name = "TXT_COMPLEMENTO")
    private String complemento;
    @Column(name = "NUM_NUMERO")
    private Integer numero;
    @Column(name = "TXT_CEP")
    private String cep;
    @Column(name = "TXT_BAIRRO")
    private String bairro;
    @Column(name = "TXT_CIDADE")
    private String cidade;
    @Column(name = "TXT_ESTADO")
    private String estado;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TB_ENDERECOS_FORNECEDOR", joinColumns = {
        @JoinColumn(name = "ID_ENDERECO")},
            inverseJoinColumns = {
                @JoinColumn(name = "ID_FORNECEDOR")})
    private List<Fornecedor> fornecedores;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TB_ENDERECOS_USUARIO", joinColumns = {
        @JoinColumn(name = "ID_ENDERECO")},
            inverseJoinColumns = {
                @JoinColumn(name = "ID_USUARIO")})
    private List<Usuario> usuarios;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Endereco)) {
            return false;
        }
        Endereco other = (Endereco) object;
        
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        
        return false;
    }
}
