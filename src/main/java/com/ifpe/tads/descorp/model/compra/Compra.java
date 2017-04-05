/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.model.compra;

import com.ifpe.tads.descorp.jpa.JpaUtil;
import com.ifpe.tads.descorp.model.fornecedor.Fornecedor;
import com.ifpe.tads.descorp.model.produto.Produto;
import com.ifpe.tads.descorp.model.usuario.Administrador;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Tiago Lima
 */
@Entity
@Table(name = "TB_COMPRA")
public class Compra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private Double valorTotal;

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_DATA_COMPRA", nullable = false)
    private Date dataCompra;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_ADMINISTRADOR", referencedColumnName = "ID")
    private Administrador administrador;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_FORNECEDOR", referencedColumnName = "ID")
    private Fornecedor fornecedor;

    @OneToMany(mappedBy = "compra", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCompra> itensCompra;

    private void calcularValorTotal() {
        double total = 0;
        for (ItemCompra item : this.itensCompra) {
            total += item.getSubTotal();
        }
        this.valorTotal = total;
    }

    public void finalizarCompras() {
        //TO DO
    }

    private void atualizarEstoque() {
        for (ItemCompra itemCompra : itensCompra) {
            Produto p = itemCompra.getProduto();
            Long disponivel = p.getQtdeDisponivel() == null ? 0 : p.getQtdeDisponivel();
            p.setQtdeDisponivel(disponivel + itemCompra.getQuantidade());
            p.atualizarProduto();
        }
    }

    public void inserirCompra() {
        EntityManagerFactory emf = JpaUtil.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {

            et.begin();

            em.persist(this);

            et.commit();

            System.out.println("Compra inserida.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void atualizarCompra() {
        EntityManagerFactory emf = JpaUtil.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {

            et.begin();

            em.merge(this);

            et.commit();

            System.out.println("Compra atualizada.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Compra selecionarCompra(Long id) {
        EntityManagerFactory emf = JpaUtil.getInstance();
        EntityManager em = emf.createEntityManager();
        Compra c = null;

        try {

            c = em.find(Compra.class, id);

            System.out.println("Compra selecionada: " + c.getId());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return c;
    }

    public void removerCompra() {
        EntityManagerFactory emf = JpaUtil.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {

            et.begin();

            em.remove(this);

            et.commit();

            System.out.println("Compra deletada.");

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

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public List<ItemCompra> getItensCompra() {
        return itensCompra;
    }

    public void setItensCompra(List<ItemCompra> itensCompra) {
        this.itensCompra = itensCompra;
    }

    public Double getValorTotal() {
        this.getAdministrador();
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;

        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }

        return false;
    }

}
