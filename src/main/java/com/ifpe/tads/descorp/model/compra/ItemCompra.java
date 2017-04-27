package com.ifpe.tads.descorp.model.compra;

import com.ifpe.tads.descorp.model.produto.Produto;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Tiago Lima
 */
@Entity
@Table(name = "TB_ITEM_COMPRA")
public class ItemCompra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "NUM_QUANTIDADE", nullable = false)
    private Integer quantidade;

    @Column(name = "NUM_PRECO_UNITARIO", nullable = false)
    private Double precoUnitario;

    @Transient
    private Double subTotal;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_PRODUTO", referencedColumnName = "ID")
    private Produto produto;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_COMPRA", referencedColumnName = "ID")
    private Compra compra;

    private void calcularSubTotal() {
        this.subTotal = this.precoUnitario * this.quantidade;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Double getSubTotal() {
        this.calcularSubTotal();
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ItemCompra)) {
            return false;
        }
        ItemCompra other = (ItemCompra) object;

        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }

        return false;
    }
    
    @Override
    public String toString() {
        return "com.ifpe.tads.descorp.model.compra.ItemCompra[ id=" + id + ":" + Double.toString(precoUnitario) + " ]";
    }
    
}
