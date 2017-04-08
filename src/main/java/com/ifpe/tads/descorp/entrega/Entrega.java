/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.entrega;

import com.ifpe.tads.descorp.jpa.JpaUtil;
import com.ifpe.tads.descorp.model.usuario.Entregador;
import com.ifpe.tads.descorp.model.venda.Venda;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Tiago Lima <tiagolimadev@outlook.com>
 */
@Entity
@Table(name = "TB_ENTREGA")
public class Entrega implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Enumerated(EnumType.STRING)
    @Column(name = "TXT_STATUS_ENTREGA")
    private StatusEntrega statusEntrega;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "DT_DATA_ENTREGA", nullable = false)
    private Date dataEntrega;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "ID_VENDA", referencedColumnName = "ID")
    private Venda venda;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "ID_ENTREGADOR", referencedColumnName = "ID")
    private Entregador entregador;
    
    public void inserirEntrega() {
        EntityManagerFactory emf = JpaUtil.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
            em.persist(this);
            et.commit();
            System.out.println("Entrega inserida.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void removerEntrega() {
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
            System.out.println("Venda removida.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static Entrega selecionarEntrega(Long id) {
        EntityManagerFactory emf = JpaUtil.getInstance();
        EntityManager em = emf.createEntityManager();
        Entrega entrega = null;

        try {
            entrega = em.find(Entrega.class, id);
            if (entrega != null) {
                System.out.println("Entrega selecionada: " + entrega.getId());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return entrega;
    }
    
    public void atualizarEntrega() {
        EntityManagerFactory emf = JpaUtil.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
            em.merge(this);
            et.commit();
            System.out.println("Entrega atualizada.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public StatusEntrega getStatusEntrega() {
        return statusEntrega;
    }

    public void setStatusEntrega(StatusEntrega statusEntrega) {
        this.statusEntrega = statusEntrega;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Entregador getEntregador() {
        return entregador;
    }

    public void setEntregador(Entregador entregador) {
        this.entregador = entregador;
    }
    
}
