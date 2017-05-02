package com.ifpe.tads.descorp.model.fornecedor;

import dbunit.DbUnitUtil;
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
 * @author Tiago Lima <tiagolimadev@outlook.com>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FornecedorTest {
    
    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;
    
    public FornecedorTest() {
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
    public void t01_inserirFornecedorValido() {
        logger.info("Executando t01: inserirFornecedorValido");
        
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome("COCA-COLA");
        fornecedor.setDescricao("EMPRESA MULTINACIONAL DE REFRIGERANTES");
        
        em.persist(fornecedor);
        em.flush();
        
        assertNotNull(fornecedor.getId());
    }
    
    @Test
    public void t02_inserirFornecedorInvalido() {
        logger.info("Executando t01: inserirFornecedorInValido");
        Fornecedor fornecedor = new Fornecedor();
        try {
            
            fornecedor.setNome("NOME INVÁLIDO PARA FORNECEDOR"
                    + " COM QUANTIDADE SUPERIOR AOS 50 CARACTERES DE LIMITE VÁLIDO"); // NOME INVÁLIDO
            fornecedor.setDescricao("DESCRIÇÃO VÁLIDA");
            
            em.persist(fornecedor);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Logger.getGlobal().info(ex.getMessage());

            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            if (logger.isLoggable(Level.INFO)) {
                for (ConstraintViolation violation : constraintViolations) {
                    Logger.getGlobal().log(Level.INFO, "{0}.{1}: {2}", new Object[]{violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage()});
                }
            }

            assertEquals(1, constraintViolations.size());
        }
    }
    
    @Test
    public void t03_atualizarFornecedorValido()
    {
        logger.info("Executando t03: atualizarCategoriaValida");
        
        TypedQuery<Fornecedor> query = em.createNamedQuery("Fornecedor.PorNome", Fornecedor.class);
        query.setParameter("nome", "SCHINCARIOL");
        
        Fornecedor fornecedor = query.getSingleResult();
        assertNotNull(fornecedor);
        fornecedor.setNome("AMBEV");
        em.flush();
        assertEquals(0, query.getResultList().size());
    }
    
    @Test
    public void t06_removerFornecedorValido()
    {
        logger.info("Executando t06: removerFornecedorValido");
        
        TypedQuery<Fornecedor> query = em.createNamedQuery("Fornecedor.PorNome", Fornecedor.class);
        query.setParameter("nome", "COCA-COLA");
        
        Fornecedor fornecedor = query.getSingleResult();
        assertNotNull(fornecedor);
        
        em.remove(fornecedor);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }
}
