package com.ifpe.tads.descorp.entrega;

import com.ifpe.tads.descorp.model.usuario.Entregador;
import com.ifpe.tads.descorp.model.venda.Venda;
import dbunit.DbUnitUtil;
import java.util.GregorianCalendar;
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
 * @author Tiago Lima <tiagolimadev@outlook.com>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EntregaTest {
    
    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;
    
    public EntregaTest() {
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
    public void t01_inserirEntregaValida() {
        logger.info("Executando t01: inserirEntregaValida");
        
        TypedQuery<Entregador> query1 = em.createNamedQuery("Usuario.PorNome", Entregador.class);
        query1.setParameter("nome", "Mirela Silva");
        Entregador entregador = query1.getSingleResult();
        
        Venda venda = em.find(Venda.class, 1L);
        
        Entrega entrega = new Entrega();
        entrega.setDataEntrega(new GregorianCalendar().getTime());
        entrega.setEntregador(entregador);
        entrega.setStatusEntrega(StatusEntrega.ESPERA);
        entrega.setVenda(venda);
        
        em.persist(entrega);
        em.flush();
        
        assertNotNull(entrega.getId());
    }
    
    @Test
    public void t02_inserirEntregaInvalida() {
        logger.info("Executando t01: inserirEntregaInValida");
        Entrega entrega = new Entrega();
        
        try {
            entrega.setDataEntrega(new GregorianCalendar().getTime());
            entrega.setStatusEntrega(StatusEntrega.CANCELADA);
            
            em.persist(entrega);
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
    public void t03_atualizarEntregaValida() {
        logger.info("Executando t03: atualizarEntregaValida");
        
        TypedQuery<Entrega> query = em.createNamedQuery("Entrega.PorDataEntrega", Entrega.class);
        query.setParameter("dataEntrega", new GregorianCalendar(2017, 4, 4).getTime());
        
        Entrega entrega = query.getSingleResult();
        entrega.setStatusEntrega(StatusEntrega.ENTREGUE);
        
        em.flush();
        assertEquals(1, query.getResultList().size());
        logger.log(Level.INFO, "Entrega {0} atualizado com sucesso.", entrega);
    }
    
    @Test
    public void t04_removerEntregaValida()
    {
        logger.info("Executando t04: removerEntregaValida");
        
        TypedQuery<Entrega> query = em.createNamedQuery("Entrega.PorStatusEntrega", Entrega.class);
        query.setParameter("statusEntrega", StatusEntrega.CANCELADA);
        
        Query queryDelete = em.createQuery("DELETE FROM Entrega e WHERE e.statusEntrega = :status");
        queryDelete.setParameter("status", StatusEntrega.CANCELADA);
        
        queryDelete.executeUpdate();
        
        em.flush();
        
        assertEquals(0, query.getResultList().size());
    }
    
}
