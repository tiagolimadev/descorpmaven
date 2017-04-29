package com.ifpe.tads.descorp.model.produto;

import dbunit.DbUnitUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
    public void t01_inserirCategoriaValida() {
        logger.info("Executando t01: inserirCategoriaValida");
        
        Categoria categoria1 = new Categoria();
        categoria1.setNome("Celulares");
        
        Categoria categoria2 = new Categoria();
        categoria2.setNome("Smartphones");
        
        List<Categoria> categorias = new ArrayList();
        categorias.add(categoria1);
        categorias.add(categoria2);
        
        Produto produto1 = new Produto();
        produto1.setCodigo("MTG5P");
        produto1.setDescricao("Smartphone 4G");
        produto1.setNome("Moto G5 Plus");
        produto1.setPreco(new BigDecimal("1499.99"));
        produto1.setQtdeDisponivel(5L);
        produto1.setCategorias(categorias);
        
        Produto produto2 = new Produto();
        produto2.setCodigo("MTG5");
        produto2.setDescricao("Smartphone 4G");
        produto2.setNome("Moto G5");
        produto2.setPreco(new BigDecimal("999.99"));
        produto2.setQtdeDisponivel(50L);
        produto2.setCategorias(categorias);
        
        em.persist(produto1);
        em.persist(produto2);
        em.flush();
        
        assertNotNull(categoria1.getId());
        logger.log(Level.INFO, "Categoria {0} incluída com sucesso.", categoria1);
        assertNotNull(categoria2.getId());
        logger.log(Level.INFO, "Categoria {0} incluída com sucesso.", categoria2);
        
        assertNotNull(produto1.getId());
        logger.log(Level.INFO, "Produto {0} incluído com sucesso.", produto1);
        assertNotNull(produto2.getId());
        logger.log(Level.INFO, "Produto {0} incluído com sucesso.", produto2);
    }

    @Test
    public void t02_inserirCategoriaInvalida() {
        logger.info("Executando t01: inserirCategoriaValida");
        Categoria categoria = new Categoria();
        try {
            
            categoria.setNome("InvalidoInvalidoInvalido");

            em.persist(categoria);
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
    public void t03_atualizarCategoriaValida()
    {
        logger.info("Executando t03: atualizarCategoriaValida");
        
        TypedQuery<Categoria> query = em.createNamedQuery("Categoria.PorNome", Categoria.class);
        query.setParameter("nome", "Bebidas");
        
        Categoria categoria = query.getSingleResult();
        assertNotNull(categoria);
        categoria.setNome("Bebidas Alcoólicas");
        em.flush();
        assertEquals(0, query.getResultList().size());
    }

    @Test
    public void t04_categoriaQuantidadeProdutos() {
        logger.info("Executando t04: Categoria.QuantidadeProdutosSQL");
        Query query = em.createNamedQuery("Categoria.QuantidadeProdutosSQL");
        query.setParameter(1, "Celulares");
        Object totalProdutos = query.getSingleResult();
        assertEquals(2L, totalProdutos);

        logger.log(Level.INFO, "Quantidade de produtos: {0}", totalProdutos);
    }
    
    @Test
    public void t05_categoriasSQL() {
        logger.info("Executando t05: SELECT ID, TXT_NOME FROM TB_CATEGORIA");
        Query query = em.createNativeQuery(
                "SELECT ID, TXT_NOME FROM TB_CATEGORIA",
                Categoria.class);
        List<Categoria> categorias = query.getResultList();
        assertEquals(3, categorias.size());

        if (logger.isLoggable(Level.INFO)) {
            for (Categoria categoria : categorias) {
                logger.log(Level.INFO, categoria.getNome());
            }
        }
    }

    @Test
    public void t06_removerCategoriaValida()
    {
        logger.info("Executando t06: removerCategoriaValida");
        TypedQuery<Categoria> query = em.createNamedQuery("Categoria.PorNome", Categoria.class);
        query.setParameter("nome", "Bebidas Alcoólicas");
        Categoria categoria = query.getSingleResult();
        assertNotNull(categoria);
        em.remove(categoria);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }
    
}
