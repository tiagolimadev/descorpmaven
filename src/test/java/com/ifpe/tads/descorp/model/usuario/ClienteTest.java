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
public class ClienteTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public ClienteTest() {
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
    public void t01_inserirClienteValido() {
        logger.info("Executando t01: inserirClienteValido");

        Cliente cliente = new Cliente();

        cliente.setCpf("87486422824");
        cliente.setDataNascimento(new GregorianCalendar(1992, 11, 1).getTime());
        cliente.setEmail("a@a.com");
        cliente.setLogin("qwer");
        cliente.setSenha("zxcasdqwe");
        cliente.setNome("Cliente");
        cliente.setTipo(TipoUsuario.CLIENTE);
        
        em.persist(cliente);
        em.flush();

        assertNotNull(cliente.getId());
        logger.log(Level.INFO, "Cliente {0} incluído com sucesso.", cliente.getId());

    }

    @Test
    public void t02_atualizarClienteValido() {
        logger.info("Executando t02: atualizarClienteValido");

        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.PorNome", Usuario.class);
        query.setParameter("nome", "XPTO");

        Cliente cliente = (Cliente) query.getSingleResult();

        cliente.setNome("XPTO 12345");
        em.flush();
        assertEquals(0, query.getResultList().size());
    }

    @Test
    public void t03_removerClienteValido() {
        logger.info("Executando t03: removerClienteValido");

        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.PorCPF", Usuario.class);
        query.setParameter("cpf", "01234567890");

        Cliente cliente = (Cliente) query.getSingleResult();

        em.remove(cliente);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }
    
    /*
    
    Criar testes de validação de unique: MySQLIntegrityConstraintViolationException
    
    */
}
