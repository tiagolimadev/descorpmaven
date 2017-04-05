/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.model.fornecedor;

import com.ifpe.tads.descorp.jpa.JpaUtil;
import com.ifpe.tads.descorp.model.endereco.Endereco;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author Tiago Lima
 */
@Entity
@Table(name = "TB_FORNECEDOR")
public class Fornecedor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "TXT_NOME")
    private String nome;
    
    @Column(name = "TXT_DESCRICAO")
    private String descricao;
    
    @ManyToMany(mappedBy = "fornecedores")
    private List<Endereco> enderecos;

    public void inserirFornecedor() {
        EntityManagerFactory emf = JpaUtil.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        
        try {
            
            et.begin();
            
            em.persist(this);
            
            et.commit();
            
            System.out.println("Fornecedor inserido.");
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void atualizarFornecedor() {
        EntityManagerFactory emf = JpaUtil.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        
        try {
            
            et.begin();
            
            em.merge(this);
            
            et.commit();
            
            System.out.println("Fornecedor atualizado.");
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static Fornecedor selecionarFornecedor(Long id) {
        EntityManagerFactory emf = JpaUtil.getInstance();
        EntityManager em = emf.createEntityManager();
        Fornecedor fornec = null;
        
        try {

            fornec = em.find(Fornecedor.class, id);

            System.out.println("Fornecedor selecionado: "+ fornec.getId());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return fornec;
    }
    
    public void removerFornecedor() {
        EntityManagerFactory emf = JpaUtil.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        
        try {
            
            et.begin();
            
            em.remove(this);
            
            et.commit();
            
            System.out.println("Fornecedor removido.");
            
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

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Fornecedor)) {
            return false;
        }
        Fornecedor other = (Fornecedor) object;
        
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        
        return false;
    }
}
