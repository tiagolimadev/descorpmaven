/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.model.usuario;

import dbunit.DbUnitUtil;
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
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Eduardo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public UsuarioTest() {
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
    public void t01_usuarioPorCPF() {
        logger.info("Executando t01: usuarioPorCPF");
        
        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.PorCPF", Usuario.class);
        query.setParameter("cpf", "66584360458");
        
        assertEquals(1, query.getResultList().size());
    }
    
    @Test
    public void t02_usuarioPorNome() {
        logger.info("Executando t02: usuarioPorNome");
        
        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.PorNome", Usuario.class);
        query.setParameter("nome", "José Silva");
        
        assertEquals(1, query.getResultList().size());
    }
    
    @Test
    public void t03_usuarioPorEmail() {
        logger.info("Executando t03: usuarioPorEmail");
        
        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.PorEmail", Usuario.class);
        query.setParameter("email", "js@a.com");
        
        assertEquals(1, query.getResultList().size());
    }
    
    @Test
    public void t04_usuarioPorTipo() {
        logger.info("Executando t04: usuarioPorTipo");
        
        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.PorTipo", Usuario.class);
        query.setParameter("tipo", TipoUsuario.CLIENTE);
        
        assertEquals(2, query.getResultList().size());
        
    }
    
    @Test
    public void t05_fazerLogin() {
        logger.info("Executando t05: fazerLogin");
        
        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.fazerLogin", Usuario.class);
        query.setParameter("login", "JS15");
        query.setParameter("senha", "123456789");
        
        
        assertEquals(1, query.getResultList().size());
        
    }

}
