/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.model.usuario;

import dbunit.DbUnitUtil;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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

        Query queryUpdate = em.createQuery("UPDATE Usuario u SET u.nome = 'XPTO 12345' WHERE u.nome = :nome");
        queryUpdate.setParameter("nome", "XPTO");
        
        queryUpdate.executeUpdate();
        
        em.flush();
        assertEquals(0, query.getResultList().size());
    }

    @Test
    public void t03_inserirClienteInvalido() {
        logger.info("Executando t03: inserirClienteInvalido");

        try {

            Cliente cliente = new Cliente();

            cliente.setCpf("1");
            cliente.setLogin("QWE");
            
            cliente.setDataNascimento(new GregorianCalendar(1992, 9, 2).getTime());
            cliente.setSenha("12345678");
            cliente.setNome("Teste");
            cliente.setEmail("asdf@asdf.com");
            cliente.setTipo(TipoUsuario.CLIENTE);

            em.persist(cliente);
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
    public void t04_atualizarClienteInvalido() {
        logger.info("Executando t04: atualizarClienteInvalido");

        try {

            TypedQuery<Usuario> query = em.createNamedQuery("Usuario.PorCPF", Usuario.class);
            query.setParameter("cpf", "01234567890");

            Cliente cliente = (Cliente) query.getSingleResult();

            cliente.setDataNascimento(new GregorianCalendar(2020, 1, 1).getTime());
            cliente.setEmail("");
            
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
    public void t05_removerClienteValido() {
        logger.info("Executando t05: removerClienteValido");

        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.PorCPF", Usuario.class);
        query.setParameter("cpf", "66584360458");

        Cliente cliente = (Cliente) query.getSingleResult();

        em.remove(cliente);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }
    
    @Test(expected = PersistenceException.class)
    public void t06_inserirClienteComCpfDuplicado(){
        logger.info("Executando t06: inserirClienteComCpfDuplicado");

        Cliente cliente = new Cliente();

        cliente.setCpf("87486422824");
        cliente.setDataNascimento(new GregorianCalendar(1992, 11, 1).getTime());
        cliente.setEmail("aasd@aasd.com");
        cliente.setLogin("qweasdr");
        cliente.setSenha("asdfasdfasdf");
        cliente.setNome("Cliente AAAAA");
        cliente.setTipo(TipoUsuario.CLIENTE);
        
        em.persist(cliente);
        em.flush();
        
    }
}
