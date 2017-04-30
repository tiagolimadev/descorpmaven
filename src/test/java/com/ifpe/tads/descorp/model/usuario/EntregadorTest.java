/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.model.usuario;

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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Eduardo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EntregadorTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public EntregadorTest() {
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
    public void t01_inserirEntregadorValido() {
        logger.info("Executando t01: inserirEntregadorValido");

        Entregador entregador = new Entregador();

        entregador.setCpf("42825377600");
        entregador.setDataNascimento(new GregorianCalendar(1975, 11, 1).getTime());
        entregador.setEmail("a@a.com");
        entregador.setLogin("erty");
        entregador.setSenha("cvbdfgert");
        entregador.setNome("Entregador");
        entregador.setTipo(TipoUsuario.ENTREGADOR);

        em.persist(entregador);
        em.flush();

        assertNotNull(entregador.getId());
        logger.log(Level.INFO, "Entregador {0} incluído com sucesso.", entregador.getId());

    }

    @Test
    public void t02_atualizarEntregadorValido() {
        logger.info("Executando t02: atualizarEntregadorValido");

        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.PorNome", Usuario.class);
        query.setParameter("nome", "Mirela Silva");

        Entregador entregador = (Entregador) query.getSingleResult();
        
        entregador.setNome("Mirela Silvana Silva");
        
        em.flush();
        assertEquals(0, query.getResultList().size());
    }

    @Test
    public void t03_removerEntregadorValido() {
        logger.info("Executando t03: removerEntregadorValido");

        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.PorCPF", Usuario.class);
        query.setParameter("cpf", "51272233766");

        Entregador entregador = (Entregador) query.getSingleResult();
        
        em.remove(entregador);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }
}
