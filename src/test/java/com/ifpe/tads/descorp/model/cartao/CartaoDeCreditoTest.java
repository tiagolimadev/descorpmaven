/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.model.cartao;

import com.ifpe.tads.descorp.model.produto.Categoria;
import com.ifpe.tads.descorp.model.usuario.*;
import dbunit.DbUnitUtil;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
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
    public void t01_inserirCartaoValido() {
        logger.info("Executando t01: inserirCartaoValido");

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
        logger.info("Executando t02: atualizarCartaoValido");

        TypedQuery<CartaoDeCredito> query = em.createNamedQuery("CartaoDeCredito.PorNomeImpresso", CartaoDeCredito.class);
        query.setParameter("nomeImpresso", "XPTO");

        CartaoDeCredito cartao = query.getSingleResult();
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
        query.setParameter("numero", "4916386629348101");

        CartaoDeCredito cartao = query.getSingleResult();

        assertNotNull(cartao.getId());
    }

    @Test
    public void t05_selecionarCartaoPorCliente() {
        logger.info("Executando t05: selecionarCartaoPorCliente");

        TypedQuery<CartaoDeCredito> query = em.createNamedQuery("CartaoDeCredito.PorCliente", CartaoDeCredito.class);
        query.setParameter("clienteId", 2L);

        CartaoDeCredito cartao = query.getSingleResult();

        assertNotNull(cartao.getId());
    }

    @Test
    public void t06_selecionarCartaoPorClienteBandeira() {
        logger.info("Executando t06: selecionarCartaoPorClienteBandeira");

        TypedQuery<CartaoDeCredito> query = em.createNamedQuery("CartaoDeCredito.PorClienteBandeira", CartaoDeCredito.class);
        query.setParameter("clienteId", 2L);
        query.setParameter("bandeira", Bandeira.ELO);

        CartaoDeCredito cartao = query.getSingleResult();

        assertNotNull(cartao.getId());
        logger.log(Level.INFO, "Cartao de Crédito {} selecionado PorClienteBandeira", cartao.getId());
    }

    
    
    @Test
    public void t07_inserirCartaoInvalido(){
        logger.info("Executando t07: inserirCartaoInvalido");
        
        CartaoDeCredito cartao = new CartaoDeCredito();
        
        try {
            
            cartao.setNomeImpresso("NOMEIMPRESSOCOMTAMANHONAOPERMITIDONOMEIMPRESSOCOMTAMANHONAOPERMITIDO");
            cartao.setNumero("0000111100001111");
            cartao.setValidade(new GregorianCalendar(1990, 5, 1).getTime());
            
            em.persist(cartao);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Logger.getGlobal().info(ex.getMessage());

            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            if (logger.isLoggable(Level.INFO)) {
                for (ConstraintViolation violation : constraintViolations) {
                    Logger.getGlobal().log(Level.INFO, "{0}.{1}: {2}", new Object[]{violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage()});
                }
            }

            assertEquals(5, constraintViolations.size());
        }
    }
    
    @Test
    public void t08_atualizarCartaoInvalido(){
        logger.info("Executando t08: atualizarCartaoInvalido");

        TypedQuery<CartaoDeCredito> query = em.createNamedQuery("CartaoDeCredito.PorClienteBandeira", CartaoDeCredito.class);
        query.setParameter("clienteId", 2L);
        query.setParameter("bandeira", Bandeira.ELO);
        
        CartaoDeCredito cartao = query.getSingleResult();
        
        try {
            
            cartao.setCliente(null);
            cartao.setNumero("2222111122221111");
            
            em.flush();
        } catch (ConstraintViolationException ex) {
            Logger.getGlobal().info(ex.getMessage());

            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            if (logger.isLoggable(Level.INFO)) {
                for (ConstraintViolation violation : constraintViolations) {
                    Logger.getGlobal().log(Level.INFO, "{0}.{1}: {2}", new Object[]{violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage()});
                }
            }

            assertEquals(2, constraintViolations.size());
        }
        
    }
    
    @Test
    public void t09_removerCartaoValido() {
        logger.info("Executando t09: removerCartaoValido");

        TypedQuery<CartaoDeCredito> query = em.createNamedQuery("CartaoDeCredito.PorClienteBandeira", CartaoDeCredito.class);
        query.setParameter("clienteId", 2L);
        query.setParameter("bandeira", Bandeira.ELO);
        
        CartaoDeCredito cartao = query.getSingleResult();
        
        em.remove(cartao);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }
    
}
