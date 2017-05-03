/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.model.compra;

import com.ifpe.tads.descorp.model.fornecedor.Fornecedor;
import com.ifpe.tads.descorp.model.produto.Produto;
import com.ifpe.tads.descorp.model.usuario.*;
import com.ifpe.tads.descorp.model.venda.Venda;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Eduardo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CompraTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public CompraTest() {
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
    public void t01_inserirCompraValida() {
        logger.info("Executando t01: inserirCompraValida");
        
        Compra compra = new Compra();

        Administrador adm = em.find(Administrador.class, 4L);
        Produto produto = em.find(Produto.class, 1L);
        Fornecedor fornecedor = em.find(Fornecedor.class, 1L);

        compra.setAdministrador(adm);
        compra.setDataCompra(new GregorianCalendar().getTime());
        compra.setItensCompra(new ArrayList<ItemCompra>());
        compra.setFornecedor(fornecedor);

        ItemCompra item = new ItemCompra();
        item.setPrecoUnitario(new BigDecimal("15.50"));
        item.setQuantidade(5);
        item.setCompra(compra);
        item.setProduto(produto);

        compra.getItensCompra().add(item);

        em.persist(compra);
        em.flush();
        assertNotNull(compra.getId());
        logger.log(Level.INFO, "Compra {0} incluída com sucesso.", compra.getId());
        
    }

    @Test
    public void t02_inserirCompraInvalida() {
        logger.info("Executando t02: inserirCompraInvalida");

        Compra compra = new Compra();

        try {

            compra.setDataCompra(new GregorianCalendar(2018, 1, 2).getTime());

            em.persist(compra);
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
    public void t03_listarComprasAdmMesAno() {
        logger.info("Executando t03: listarComprasAdmMesAno");

        TypedQuery<Compra> query = em.createNamedQuery("Compra.PorAdministradorMesAno", Compra.class);
        query.setParameter("cpf", "58166424720");
        query.setParameter("mes", 4);
        query.setParameter("ano", 2017);
        
        assertEquals(2, query.getResultList().size());
        
    }
    
    @Test
    public void t04_removerCompraValida() {
        logger.info("Executando t04: removerCompraValida");

        TypedQuery<Compra> query = em.createNamedQuery("Compra.PorAdministradorMesAno", Compra.class);
        query.setParameter("cpf", "58166424720");
        query.setParameter("mes", 4);
        query.setParameter("ano", 2017);

        Compra compra = em.find(Compra.class, 2L);

        em.remove(compra);
        em.flush();
        assertEquals(1, query.getResultList().size());
        
    }
    
}
