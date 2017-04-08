/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.model.cartao;

import com.ifpe.tads.descorp.jpa.JpaUtil;
import com.ifpe.tads.descorp.model.usuario.Cliente;
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
 * @author Tiago Lima
 */
@Entity
@Table(name = "TB_CARTAO_CREDITO")
public class CartaoDeCredito implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "TXT_NUMERO")
    private String numero;
    
    @Column(name = "TXT_NOME")
    private String nome;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "DT_VALIDADE")
    private Date validade;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "TXT_BANDEIRA")
    private Bandeira bandeira;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID")
    private Cliente cliente;

    public static CartaoDeCredito selecionarCartao(Long id){
        EntityManagerFactory emf = JpaUtil.getInstance();
        EntityManager em = emf.createEntityManager();
        CartaoDeCredito cartao = null;
        
        try {
            
            cartao = em.find(CartaoDeCredito.class, id);
            
            if(cartao != null){
                System.out.println("Cartão selecionado: "+ cartao.getId());
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return cartao;
    }
    
    public void inserirCartao() {
        EntityManagerFactory emf = JpaUtil.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {

            et.begin();

            em.persist(this);

            et.commit();

            System.out.println("Cartão inserido.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void atualizarCartao() {
        EntityManagerFactory emf = JpaUtil.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {

            et.begin();

            em.merge(this);

            et.commit();

            System.out.println("Cartão atualizado.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void removerCartao() {
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

            System.out.println("Cartão removido.");

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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public Bandeira getBandeira() {
        return bandeira;
    }

    public void setBandeira(Bandeira bandeira) {
        this.bandeira = bandeira;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CartaoDeCredito)) {
            return false;
        }
        CartaoDeCredito other = (CartaoDeCredito) object;
        
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        
        return false;
    }
}
