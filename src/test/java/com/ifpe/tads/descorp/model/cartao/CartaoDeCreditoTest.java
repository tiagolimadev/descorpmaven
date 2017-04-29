/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.model.cartao;

import com.ifpe.tads.descorp.model.usuario.*;
import dbunit.DbUnitUtil;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 * @author Eduardo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CartaoDeCreditoTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public CartaoDeCreditoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        logger = Logger.getGlobal();
        logger.setLevel(Level.INFO);
        emf = Persistence.createEntityManagerFactory("descorp");
        DbUnitUtil.inserirDados();
    }

    @AfterClass
    public static void tearDownClass() {
        emf.close();
    }

    @Before
    public void setUp() {
        em = emf.createEntityManager();
        et = em.getTransaction();
        et.begin();
    }

    @After
    public void tearDown() {
        try {
            et.commit();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage());

            if (et.isActive()) {
                et.rollback();
            }
        } finally {
            em.close();
            em = null;
            et = null;
        }
    }

    @Test
    public void t01_inserirCartaoDeCreditoValido() {
        logger.info("Executando t01: inserirCartaoDeCreditoValido");

        CartaoDeCredito cartao = new CartaoDeCredito();
        Cliente cliente = em.find(Cliente.class, 1L);
        
        cartao.setBandeira(Bandeira.VISA);
        cartao.setCliente(cliente);
        cartao.setNomeImpresso("ZÉ RMS");
        cartao.setValidade(new GregorianCalendar(2019, 5, 1).getTime());
        cartao.setNumero("4024007168509924");

        em.persist(cartao);
        em.flush();

        assertNotNull(cartao.getId());
        logger.log(Level.INFO, "CartaoDeCredito {0} incluído com sucesso.", cartao.getId());

    }

    @Test
    public void t02_atualizarCartaoValido() {
        logger.info("Executando t02: atualizarCartaoDeCreditoValido");

        TypedQuery<CartaoDeCredito> query = em.createNamedQuery("CartaoDeCredito.PorNomeImpresso", CartaoDeCredito.class);
        query.setParameter("nomeImpresso", "ZÉ RMS");

        CartaoDeCredito cartao = (CartaoDeCredito) query.getSingleResult();
        assertNotNull(cartao);

        cartao.setNomeImpresso("JOSÉ R M Silva");
        em.flush();
        assertEquals(0, query.getResultList().size());
    }

    @Test
    public void t03_selecionarCartaoPorNomeImpresso() {
        logger.info("Executando t03: selecionarCartaoPorNomeImpresso");

        TypedQuery<CartaoDeCredito> query = em.createNamedQuery("CartaoDeCredito.PorNomeImpresso", CartaoDeCredito.class);
        query.setParameter("nomeImpresso", "JOSÉ R M SILVA");

        CartaoDeCredito cartao = query.getSingleResult();

        assertNotNull(cartao.getId());
    }

    @Test
    public void t04_selecionarCartaoPorNumero() {
        logger.info("Executando t04: selecionarCartaoPorNumero");

        TypedQuery<CartaoDeCredito> query = em.createNamedQuery("CartaoDeCredito.PorNumero", CartaoDeCredito.class);
        query.setParameter("numero", "4024007168509924");

        CartaoDeCredito cartao = query.getSingleResult();

        assertNotNull(cartao.getId());
    }

    @Test
    public void t05_selecionarCartaoPorCliente() {
        logger.info("Executando t05: selecionarCartaoPorCliente");

        TypedQuery<CartaoDeCredito> query = em.createNamedQuery("CartaoDeCredito.PorCliente", CartaoDeCredito.class);
        query.setParameter("clienteId", 1L);

        CartaoDeCredito cartao = query.getSingleResult();

        assertNotNull(cartao.getId());
    }

    @Test
    public void t06_selecionarCartaoPorClienteBandeira() {
        logger.info("Executando t06: selecionarCartaoPorClienteBandeira");

        TypedQuery<CartaoDeCredito> query = em.createNamedQuery("CartaoDeCredito.PorClienteBandeira", CartaoDeCredito.class);
        query.setParameter("clienteId", 1L);
        query.setParameter("bandeira", Bandeira.VISA);

        CartaoDeCredito cartao = query.getSingleResult();

        assertNotNull(cartao.getId());
        logger.log(Level.INFO, "Cartao de Crédito {} selecionado PorClienteBandeira", cartao.getId());
    }

    @Test
    public void t07_removerCartaoPorValido() {
        logger.info("Executando t07: removerCartaoDeCreditoValido");

        TypedQuery<CartaoDeCredito> query = em.createNamedQuery("CartaoDeCredito.PorNomeImpresso", CartaoDeCredito.class);
        query.setParameter("nomeImpresso", "JOSÉ R M SILVA");

        CartaoDeCredito cartao = (CartaoDeCredito) query.getSingleResult();
        
        em.remove(cartao);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }

}
