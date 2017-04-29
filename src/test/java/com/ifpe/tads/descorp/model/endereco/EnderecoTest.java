package com.ifpe.tads.descorp.model.endereco;

import com.ifpe.tads.descorp.model.usuario.Usuario;
import dbunit.DbUnitUtil;
import java.util.ArrayList;
import java.util.List;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Tiago Lima <tiagolimadev@outlook.com>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EnderecoTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public EnderecoTest() {
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
    public void t01_inserirEnderecoValido() {
        logger.info("Executando t01: inserirEnderecoValido");
        
        List<Usuario> usuarios = new ArrayList<>();
        
        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.PorNome", Usuario.class);
        query.setParameter("nome", "XPTO");
        Usuario usuario = query.getSingleResult();
        
        usuarios.add(usuario);
        
        Endereco endereco = new Endereco();
        endereco.setLogradouro("RUA NOVA");
        endereco.setComplemento("CASA");
        endereco.setNumero(43);
        endereco.setCep("53.280-090");
        endereco.setBairro("SAPUCAIA");
        endereco.setCidade("OLINDA");
        endereco.setEstado("PERNAMBUCO");
        endereco.setUsuarios(usuarios);
        
        em.persist(endereco);
        em.flush();
        
        assertNotNull(endereco.getId());
    }
    
    @Test
    public void t02_inserirEnderecoInvalido() {
        logger.info("Executando t02: inserirEnderecoInvalido");
        Endereco endereco = new Endereco();
        
        try {
            endereco.setLogradouro("LOGRADOURO VÁLIDO");
            endereco.setComplemento("COMPLEMENTO VÁLIDO");
            endereco.setNumero(0); // NÚMERO INVÁLIDO
            endereco.setCep("53280-090"); // CEP INVÁLIDO
            endereco.setBairro("BAIRRO INVÁLIDo ACIMA DE 50 CARACATERES MÁXIMOS EXIGIDOS"); // BAIRRO INVÁLIDO
            endereco.setCidade("CIDADE INVÁLIDA ACIMA DE 50 CARACATERES MÁXIMOS EXIGIDOS"); // CIDADE INVÁLIDA
            endereco.setEstado("ESTADO INVÁLIDO ACIMA DE 50 CARACATERES MÁXIMOS EXIGIDOS"); // ESTADO INVÁLIDO
            
            em.persist(endereco);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Logger.getGlobal().info(ex.getMessage());

            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            if (logger.isLoggable(Level.INFO)) {
                for (ConstraintViolation violation : constraintViolations) {
                    Logger.getGlobal().log(Level.INFO, "{0}.{1}: {2}", new Object[]{violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage()});
                }
            }

            assertEquals(5, constraintViolations.size());
        }
    }
    
    @Test
    public void t03_atualizarEnderecoValido() {
        logger.info("Executando t03: atualizarEnderecoValido");
        
        TypedQuery<Endereco> query = em.createNamedQuery("Endereco.PorCep", Endereco.class);
        query.setParameter("cep", "53.280-090");
        
        Endereco endereco = query.getSingleResult();
        assertNotNull(endereco);
        endereco.setLogradouro("1ª TRAVESSA DA RUA NOVA");
        
        em.flush();
        assertEquals(1, query.getResultList().size());
        logger.log(Level.INFO, "Endereco {0} atualizado com sucesso.", endereco);
    }
    
    @Test
    public void t04_removerEnderecoValido()
    {
        logger.info("Executando t04: removerEnderecoValido");
        
        TypedQuery<Endereco> query = em.createNamedQuery("Endereco.PorLogradouro", Endereco.class);
        query.setParameter("logradouro", "1ª TRAVESSA DA RUA NOVA");
        Endereco endereco = query.getSingleResult();
        
        assertNotNull(endereco);
        
        em.remove(endereco);
        em.flush();
        
        assertEquals(0, query.getResultList().size());
    }
    
}
