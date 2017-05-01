package com.ifpe.tads.descorp.model.venda;

import com.ifpe.tads.descorp.model.produto.Produto;
import dbunit.DbUnitUtil;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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
public class ItemVendaTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public ItemVendaTest() {
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
    public void t01_inserirItemVendaValido() {
        logger.info("Executando t01: inserirItemVendaValido");

        Venda venda = em.find(Venda.class, 1L);
        Produto produto = em.find(Produto.class, 1L);

        ItemVenda item = new ItemVenda();
        
        item.setPrecoUnitario(new BigDecimal("25.90"));
        item.setQuantidade(15);
        item.setVenda(venda);
        item.setProduto(produto);
        
        venda.setDataVenda(new GregorianCalendar().getTime());
        
        venda.getItensVenda().add(item);

        em.flush();

        assertNotNull(item.getId());

        logger.log(Level.INFO, "ItemVenda {0} incluída com sucesso.", venda.getId());

    }

    @Test
    public void t02_inserirItemVendaInvalido() {
        logger.info("Executando t02: inserirItemVendaInvalido");

        try {

            ItemVenda item = em.find(ItemVenda.class, 1L);
            item.setPrecoUnitario(new BigDecimal("-10.90"));
            item.setQuantidade(-2);
            
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

}
