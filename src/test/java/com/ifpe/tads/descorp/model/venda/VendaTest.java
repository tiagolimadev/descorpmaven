package com.ifpe.tads.descorp.model.venda;

import com.ifpe.tads.descorp.model.usuario.Cliente;
import dbunit.DbUnitUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
 * @author Eduardo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VendaTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;
    private static Long clienteId;

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
        Cliente cliente = new Cliente();

        cliente.setNome("Eduardo Amaral");
        cliente.setCpf("01234567890");
        cliente.setDataNascimento(new GregorianCalendar(1994, 4, 1).getTime());
        cliente.setEmail("efsa@ifpe.edu.br");
        cliente.setLogin("eduardo");
        cliente.setSenha("123456789");

        em.persist(cliente);
        em.flush();

        assertNotNull(cliente.getId());
        clienteId = cliente.getId();
        logger.log(Level.INFO, "Cliente {0} incluído com sucesso.", cliente);

        venda.setCliente(cliente);
        venda.setDataVenda(new Date());

        em.persist(venda);
        em.flush();
        assertNotNull(venda.getId());
        logger.log(Level.INFO, "Venda {0} incluída com sucesso.", venda.getId());

    }

    @Test
    public void t02_atualizarVendaValida() {
        logger.info("Executando t02: atualizarVendaValida");
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, 20);

        TypedQuery<Venda> query = em.createNamedQuery("Venda.PorDataCliente", Venda.class);
        query.setParameter("clienteId", clienteId);
        query.setParameter("dataVenda", new Date());

        Venda venda = query.getSingleResult();
        assertNotNull(venda.getId());
        logger.log(Level.INFO, "Venda {0} selecionar PorDataCliente", venda.getId());
        
        venda.setDataVenda(calendar.getTime());
        em.flush();
        assertEquals(0, query.getResultList().size());
    }

    @Test
    public void t03_removerVendaValida() {
        logger.info("Executando t03: removerVendaValida");
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, 20);

        TypedQuery<Venda> query = em.createNamedQuery("Venda.PorDataCliente", Venda.class);
        query.setParameter("clienteId", clienteId);
        query.setParameter("dataVenda", calendar.getTime());

        Venda venda = query.getSingleResult();
        assertNotNull(venda.getId());

        em.remove(venda);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }

}
