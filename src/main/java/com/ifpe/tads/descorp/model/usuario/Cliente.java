/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.model.usuario;

import com.ifpe.tads.descorp.jpa.JpaUtil;
import com.ifpe.tads.descorp.model.cartao.CartaoDeCredito;
import com.ifpe.tads.descorp.model.venda.Venda;
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
@Table(name = "TB_CLIENTE")
@DiscriminatorValue(value = "C")
@PrimaryKeyJoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID")
public class Cliente extends Usuario implements Serializable {

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartaoDeCredito> cartoes;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Venda> vendas;

    public List<CartaoDeCredito> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<CartaoDeCredito> cartoes) {
        this.cartoes = cartoes;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }

    public static Cliente selecionarCliente(Long id) {
        EntityManagerFactory emf = JpaUtil.getInstance();
        EntityManager em = emf.createEntityManager();
        Cliente cliente = null;

        cliente = em.find(Cliente.class, id);

        return cliente;
    }

}
