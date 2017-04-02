/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.jpa;

import com.ifpe.tads.descorp.model.usuario.Administrador;
import com.ifpe.tads.descorp.model.usuario.TipoUsuario;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author tiagolima
 */
public class AdministradorJPA {
    public static void main(String[] args) {
        Integer id = 1;
        
        criarAdministrador();
        atualizarAdministrador(id);
    }
    
    public static void criarAdministrador() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction et = null;
        
        Administrador admin = preencherAdministrador();
        
        try {
            emf = Persistence.createEntityManagerFactory("descorp");
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            em.persist(admin);
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }
    
    public static void atualizarAdministrador(Integer id) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction et = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("descorp");
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            Administrador admin = em.find(Administrador.class, id.intValue());
            admin.setCpf("987.654.321-00");
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }
    
    public static Administrador preencherAdministrador() {
        Administrador admin = new Administrador();
        admin.setNome("Marcos Costa");
        admin.setCpf("123.456.789-10");
        admin.setDataNascimento(new GregorianCalendar(1976, 12, 31).getTime());
        admin.setEmail("vader@star.wars");
        admin.setLogin("TioDarth");
        admin.setSenha("SantaCruz");
        admin.setTipo(TipoUsuario.ADMINISTRADOR);
        
        return admin;
    }
}
