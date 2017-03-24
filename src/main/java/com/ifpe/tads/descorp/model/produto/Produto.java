/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.model.produto;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Tiago Lima
 */
@Entity
@Table(name = "TB_PRODUTO")
public class Produto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TXT_CODIGO")
    private String codigo;
    @Column(name = "TXT_NOME")
    private String nome;
    @Column(name = "TXT_DESCRICAO")
    private String descricao;
    @Column(name = "NUM_PRECO")
    private Double preco;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TB_PRODUTOS_CATEGORIAS", joinColumns = {
        @JoinColumn(name = "ID_PRODUTO")},
            inverseJoinColumns = {
                @JoinColumn(name = "ID_CATEGORIA")
    })
    private List<Categoria> categorias;
    @OneToMany(mappedBy = "produto", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCompra> itensCompras;
    @OneToMany(mappedBy = "produto", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCompra> itensVendas;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public List<ItemCompra> getItensCompras() {
        return itensCompras;
    }

    public void setItensCompras(List<ItemCompra> itensCompras) {
        this.itensCompras = itensCompras;
    }

    public List<ItemCompra> getItensVendas() {
        return itensVendas;
    }

    public void setItensVendas(List<ItemCompra> itensVendas) {
        this.itensVendas = itensVendas;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Produto)) {
            return false;
        }
        Produto other = (Produto) object;
        
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        
        return false;
    }
    
}
