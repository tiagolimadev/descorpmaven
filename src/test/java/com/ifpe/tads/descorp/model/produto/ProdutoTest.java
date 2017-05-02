package com.ifpe.tads.descorp.model.produto;

import dbunit.DbUnitUtil;
import java.math.BigDecimal;
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
public class ProdutoTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public ProdutoTest() {
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
    public void t01_inserirProdutoValido() {
        logger.info("Executando t01: inserirProdutoValido");

        Produto produto = new Produto();
        produto.setNome("Troféu da Copa do Nordeste");
        produto.setCodigo("TCN2017");
        produto.setDescricao("Troféu do Santa Cruz");
        produto.setQtdeDisponivel(1L);
        produto.setPreco(new BigDecimal("150000"));

        em.persist(produto);
        em.flush();

        assertNotNull(produto.getId());
        logger.log(Level.INFO, "Produto {0} inserido com sucesso.", produto);
    }

    @Test
    public void t02_inserirProdutoInvalido() {
        logger.info("Executando t02: inserirProdutoInvalido");
        Produto produto = new Produto();

        try {
            produto.setNome("NOME DE PRODUTO INVÁLIDO ACIMA DE "
                    + "20 CARACATERES MÁXIMOS EXIGIDOS"); // NOME INVÁLIDO
            produto.setCodigo("CÓDIGO DE PRODUTO INVÁLIDO ACIMA DE "
                    + "20 CARACATERES MÁXIMOS EXIGIDOS"); // CÓDIGO INVÁLIDO
            produto.setDescricao("DESCRIÇÃO VÁLIDA");
            produto.setPreco(new BigDecimal("0.0")); // PREÇO INVÁLIDO
            produto.setQtdeDisponivel(300000L); // QTDE DISPONÍVEL INVÁLIDA

            em.persist(produto);
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
    public void t03_atualizarProdutoValido() {
        logger.info("Executando t03: atualizarProdutoValido");

        TypedQuery<Produto> query = em.createNamedQuery("Produto.PorCodigo", Produto.class);
        query.setParameter("codigo", "SKLN100");

        Produto produto = query.getSingleResult();
        assertNotNull(produto);
        produto.setNome("Cerveja Skol Long Neck");

        em.flush();
        assertEquals(1, query.getResultList().size());
        logger.log(Level.INFO, "Produto {0} atualizado com sucesso.", produto);
    }

    @Test
    public void t04_selecionarProdutoMaisBarato() {
        logger.info("Executando t04: selecionarProdutoMaisBarato");

        TypedQuery<Produto> query = em.createNamedQuery("Produto.PorMenorPrecoSQL", Produto.class);

        Produto produto = query.getSingleResult();

        assertEquals(new BigDecimal("6.00"), produto.getPreco().setScale(2));
        logger.log(Level.INFO, "Produto {0} mais barato encontrado.", produto);
    }

    @Test
    public void t06_removerProdutoValido() {
        logger.info("Executando t06: removerProdutoValido");

        TypedQuery<Produto> query = em.createNamedQuery("Produto.PorMaiorPrecoSQL", Produto.class);
        Produto produto = query.getSingleResult();

        assertNotNull(produto);

        em.remove(produto);
        em.flush();

        assertEquals(1, query.getResultList().size());
    }
}
