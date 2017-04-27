package com.ifpe.tads.descorp.model.produto;

import dbunit.DbUnitUtil;
import java.util.ArrayList;
import java.util.List;
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
 * @author Tiago Lima <tiagorodrigodelima@hotmail.com>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CategoriaTest {
    
    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;
    
    public CategoriaTest() {
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
    public void t01_criarCategoriaValida() {
        logger.info("Executando t01: inserirCategoriaValida");
        List<Produto> produtos = new ArrayList();
        Categoria categoria = new Categoria();
        categoria.setNome("Celulares");
        
        Produto produto1 = new Produto();
        produto1.setCodigo("MTG5P");
        produto1.setDescricao("Smartphone 4G");
        produto1.setNome("Moto G5 Plus");
        produto1.setPreco(1499.99);
        produto1.setQtdeDisponivel(5L);
        produtos.add(produto1);
        
        Produto produto2 = new Produto();
        produto2.setCodigo("MTG5");
        produto2.setDescricao("Smartphone 4G");
        produto2.setNome("Moto G5");
        produto2.setPreco(999.99);
        produto2.setQtdeDisponivel(10L);
        produtos.add(produto2);
        
        categoria.setProdutos(produtos);
        
        em.persist(categoria);
        em.flush();
        assertNotNull(produto1.getId());
        logger.log(Level.INFO, "Produto {0} incluído com sucesso.", produto1);
        assertNotNull(produto2.getId());
        logger.log(Level.INFO, "Produto {0} incluído com sucesso.", produto2);
        assertNotNull(categoria.getId());
        logger.log(Level.INFO, "Categoria {0} incluída com sucesso.", categoria);
    }
    
    @Test
    public void t02_atualizarCategoriaValida()
    {
        logger.info("Executando t02: atualizarCategoriaValida");
        TypedQuery<Categoria> query = em.createNamedQuery("Categoria.PorNome", Categoria.class);
        query.setParameter("nome", "Bebidas");
        Categoria categoria = query.getSingleResult();
        assertNotNull(categoria);
        categoria.setNome("Bebidas Alcoólicas");
        em.flush();
        assertEquals(0, query.getResultList().size());
    }
    
    @Test
    public void t03_removerCategoriaValida()
    {
        logger.info("Executando t03: removerCategoriaValida");
        TypedQuery<Categoria> query = em.createNamedQuery("Categoria.PorNome", Categoria.class);
        query.setParameter("nome", "Bebidas Alcoólicas");
        Categoria categoria = query.getSingleResult();
        assertNotNull(categoria);
        em.remove(categoria);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }
    
}
