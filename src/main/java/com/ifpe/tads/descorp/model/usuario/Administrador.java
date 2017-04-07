/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.model.usuario;

import com.ifpe.tads.descorp.jpa.JpaUtil;
import com.ifpe.tads.descorp.model.compra.Compra;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Tiago Lima
 */
@Entity
@Table(name = "TB_ADMINISTRADOR")
@DiscriminatorValue(value = "A")
@PrimaryKeyJoinColumn(name = "ID_ADMINISTRADOR", referencedColumnName = "ID")
public class Administrador extends Usuario implements Serializable {
    
    @OneToMany(mappedBy = "administrador", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Compra> compras;

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }
    
    public static Administrador selecionarAdministrador(Long id){
        EntityManagerFactory emf = JpaUtil.getInstance();
        EntityManager em = emf.createEntityManager();
        Administrador adm = null;

        adm = em.find(Administrador.class, id);

        return adm;
    }
    
}
