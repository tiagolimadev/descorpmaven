package com.ifpe.tads.descorp.model.venda;

import com.ifpe.tads.descorp.model.produto.Produto;
import com.ifpe.tads.descorp.model.usuario.Cliente;
import dbunit.DbUnitUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TemporalType;
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
public class VendaTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public VendaTest() {
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
    public void t01_inserirVendaValida() {
        logger.info("Executando t01: inserirVendaValida");

        Venda venda = new Venda();

        Cliente cliente = em.find(Cliente.class, 1L);
        Produto produto = em.find(Produto.class, 1L);

        assertNotNull(cliente.getId());
        logger.log(Level.INFO, "Cliente {0} incluído com sucesso.", cliente);

        venda.setCliente(cliente);
        venda.setDataVenda(new GregorianCalendar().getTime());
        venda.setItensVenda(new ArrayList<ItemVenda>());

        ItemVenda item = new ItemVenda();
        item.setPrecoUnitario(new BigDecimal("5.50"));
        item.setQuantidade(5);
        item.setVenda(venda);
        item.setProduto(produto);

        venda.getItensVenda().add(item);

        em.persist(venda);
        em.flush();
        assertNotNull(venda.getId());
        logger.log(Level.INFO, "Venda {0} incluída com sucesso.", venda.getId());

    }

    @Test
    public void t02_inserirVendaInvalida() {
        logger.info("Executando t02: inserirVendaInvalida");

        Venda venda = new Venda();

        try {

            venda.setDataVenda(new GregorianCalendar(1992, 1, 2).getTime());

            em.persist(venda);
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
    public void t03_atualizarVendaInvalida() {
        logger.info("Executando t03: atualizarVendaInvalida");

        TypedQuery<Venda> query = em.createNamedQuery("Venda.PorDataCliente", Venda.class);
        query.setParameter("clienteId", 2L);
        query.setParameter("dataVenda", new GregorianCalendar(2017, 2, 15).getTime(), TemporalType.DATE);

        Venda venda = query.getSingleResult();

        try {

            venda.setDataVenda(new GregorianCalendar(2014, 3, 1).getTime());

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
    public void t05_removerVendaValida() {
        logger.info("Executando t05: removerVendaValida");

        TypedQuery<Venda> query = em.createNamedQuery("Venda.PorDataCliente", Venda.class);
        query.setParameter("clienteId", 2L);
        query.setParameter("dataVenda", new GregorianCalendar(2017, 2, 15).getTime(), TemporalType.DATE);

        Venda venda = query.getSingleResult();

        em.remove(venda);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }

}
