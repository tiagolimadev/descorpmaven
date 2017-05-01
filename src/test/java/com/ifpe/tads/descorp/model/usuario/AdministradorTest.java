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
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
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
public class AdministradorTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public AdministradorTest() {
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
    public void t01_inserirAdministradorValido() {
        logger.info("Executando t01: inserirAdministradorValido");

        Administrador adm = new Administrador();

        adm.setCpf("34617066390");
        adm.setDataNascimento(new GregorianCalendar(2001, 3, 14).getTime());
        adm.setEmail("a@a.com");
        adm.setLogin("adbc");
        adm.setSenha("12345678");
        adm.setNome("ADM");
        adm.setTipo(TipoUsuario.ADMINISTRADOR);
        
        em.persist(adm);
        em.flush();

        assertNotNull(adm.getId());
        logger.log(Level.INFO, "Administrador {0} incluído com sucesso.", adm.getId());

    }
    
    @Test
    public void t02_atualizarAdministradorValido() {
        logger.info("Executando t02: atualizarAdministradorValido");

        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.PorEmail", Usuario.class);
        query.setParameter("email", "ms@a.com");

        Administrador adm = (Administrador) query.getSingleResult();

        adm.setEmail("qwe@a.com");
        em.flush();
        assertEquals(0, query.getResultList().size());
    }

    @Test
    public void t03_inserirAdministradorInvalido() {
        logger.info("Executando t03: inserirAdministradorInvalido");

        try {

            Administrador adm = new Administrador();

            adm.setCpf("1");
            adm.setLogin("1");
            adm.setSenha("1");
            adm.setNome("");
            
            adm.setDataNascimento(new GregorianCalendar(1952, 2, 5).getTime());
            adm.setEmail("a@a.com");
            adm.setTipo(TipoUsuario.ADMINISTRADOR);

            em.persist(adm);
            em.flush();

        } catch (ConstraintViolationException ex) {
            Logger.getGlobal().info(ex.getMessage());

            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            if (logger.isLoggable(Level.INFO)) {
                for (ConstraintViolation violation : constraintViolations) {
                    Logger.getGlobal().log(Level.INFO, "{0}.{1}: {2}", new Object[]{violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage()});
                }
            }

            assertEquals(4, constraintViolations.size());
        }

    }

    @Test
    public void t04_atualizarAdministradorInvalido() {
        logger.info("Executando t04: atualizarAdministradorInvalido");

        try {

            TypedQuery<Usuario> query = em.createNamedQuery("Usuario.PorCPF", Usuario.class);
            query.setParameter("cpf", "58166424720");

            Administrador adm = (Administrador) query.getSingleResult();

            adm.setNome("ASDFASDFASDFASDFASDFASDFASDFASDFASDFASDFASDFASDFASDFASDFASDFASDFASDFASDFASDFASDFASDFASDFASDFASDFASDFASDFASDFASDFASDFASDFASDF");
            adm.setSenha("ASDFASDFASDFASDFASDFASDFASDFASDF");
            adm.setLogin("ASDFASDFASDFASDFASDFASDFASDFASDF");
            
            em.flush();

        } catch (ConstraintViolationException ex) {
            Logger.getGlobal().info(ex.getMessage());

            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            if (logger.isLoggable(Level.INFO)) {
                for (ConstraintViolation violation : constraintViolations) {
                    Logger.getGlobal().log(Level.INFO, "{0}.{1}: {2}", new Object[]{violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage()});
                }
            }

            assertEquals(3, constraintViolations.size());
        }

    }
    
    @Test
    public void t05_removerAdministradorValido() {
        logger.info("Executando t05: removerAdministradorValido");

        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.PorCPF", Usuario.class);
        query.setParameter("cpf", "58166424720");
        
        Administrador adm = (Administrador) query.getSingleResult();

        em.remove(adm);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }

}
