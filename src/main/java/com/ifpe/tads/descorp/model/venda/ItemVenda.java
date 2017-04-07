/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.model.venda;

import com.ifpe.tads.descorp.jpa.JpaUtil;
import com.ifpe.tads.descorp.model.produto.Produto;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
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
@Table(name = "TB_ITEM_VENDA")
public class ItemVenda implements Serializable {
    
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
    @JoinColumn(name = "ID_VENDA", referencedColumnName = "ID")
    private Venda venda;

    private void calcularSubTotal(){
        this.subTotal = this.precoUnitario * this.quantidade;
    }
    
    public void inserirItemVenda() {
        EntityManagerFactory emf = JpaUtil.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        
        try {
            
            et.begin();
            
            em.persist(this);
            
            et.commit();
            
            System.out.println("ItemVenda inserido.");
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void atualizarItemVenda() {
        EntityManagerFactory emf = JpaUtil.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        
        try {
            
            et.begin();
            
            em.merge(this);
            
            et.commit();
            
            System.out.println("ItemVenda atualizado.");
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static ItemVenda selecionarItemVenda(Long id) {
        EntityManagerFactory emf = JpaUtil.getInstance();
        EntityManager em = emf.createEntityManager();
        ItemVenda v = null;
        
        try {

            v = em.find(ItemVenda.class, id);

            System.out.println("Compra selecionado: "+ v.getId());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return v;
    }
    
    public void removerItemVenda() {
        EntityManagerFactory emf = JpaUtil.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        
        try {
            
            et.begin();
            
            if (em.contains(this)) {
                em.remove(this);
            } else {
                em.remove(em.merge(this));
            }
            
            et.commit();
            
            System.out.println("ItemVenda removido.");
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ItemVenda)) {
            return false;
        }
        ItemVenda other = (ItemVenda) object;
        
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        
        return false;
    }
}
