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
public class OperadorTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public OperadorTest() {
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
    public void t01_inserirOperadorValido() {
        logger.info("Executando t01: inserirOperadorValido");

        Operador operador = new Operador();

        operador.setCpf("69814632457");
        operador.setDataNascimento(new GregorianCalendar(1954, 5, 15).getTime());
        operador.setEmail("email@email.com");
        operador.setLogin("qwerty");
        operador.setSenha("12341234");
        operador.setNome("Operador");
        operador.setTipo(TipoUsuario.OPERADOR);

        em.persist(operador);
        em.flush();

        assertNotNull(operador.getId());
        logger.log(Level.INFO, "Operador {0} incluído com sucesso.", operador.getId());

    }

    @Test
    public void t02_atualizarOperadorValido() {
        logger.info("Executando t02: atualizarOperadorValido");

        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.PorEmail", Usuario.class);
        query.setParameter("email", "cs@a.com");

        Operador operador = (Operador) query.getSingleResult();

        operador.setEmail("asd@a.com");
        em.flush();
        assertEquals(0, query.getResultList().size());
    }

    @Test
    public void t03_removerOperadorValido() {
        logger.info("Executando t03: removerOperadorValido");

        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.PorCPF", Usuario.class);
        query.setParameter("cpf", "36127403693");

        Operador operador = (Operador) query.getSingleResult();

        em.remove(operador);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }
}
