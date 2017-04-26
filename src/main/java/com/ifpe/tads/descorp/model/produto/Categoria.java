/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.model.produto;

import com.ifpe.tads.descorp.jpa.JpaUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author Tiago Lima
 */
@Entity
@Table(name = "TB_CATEGORIA")
public class Categoria implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 20)
    @Column(name = "TXT_NOME")
    private String nome;
    @Valid
    @ManyToMany(mappedBy = "categorias", cascade = CascadeType.PERSIST)
    private List<Produto> produtos;
    
    public void inserirCategoria() {
        EntityManagerFactory emf = JpaUtil.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
            em.persist(this);
            et.commit();
            System.out.println("Categoria inserida.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void atualizarCategoria() {
        EntityManagerFactory emf = JpaUtil.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
            em.merge(this);
            et.commit();
            System.out.println("Categoria atualizada.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void removerCategoria() {
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
    
    public static Categoria selecionarCategoria(Long id) {
        EntityManagerFactory emf = JpaUtil.getInstance();
        EntityManager em = emf.createEntityManager();
        Categoria categoria = null;

        try {
            categoria = em.find(Categoria.class, id);
            if (categoria != null) {
                System.out.println("Categoria selecionada: " + categoria.getNome());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return categoria;
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

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
        
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        
        return false;
    }
    
}
