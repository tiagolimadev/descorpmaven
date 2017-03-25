/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.jpa;

import com.ifpe.tads.descorp.model.usuario.Cliente;
import com.ifpe.tads.descorp.model.usuario.TipoUsuario;
import com.ifpe.tads.descorp.model.usuario.Usuario;
import com.ifpe.tads.descorp.model.venda.Venda;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Maurício
 */
public class UsuarioJpa {
    public static void main(String[] args) {
        Cliente c = new Cliente();
        c.setNome("XXXX");
        c.setCpf("01239123");
        c.setEmail("sadasfasf@asfasf.com");
        c.setDataNascimento(new Date());
        c.setLogin("AAA");
        c.setSenha("mmmm");
        
        System.out.println(c.getCpf());
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction et = null;
        try {
            emf = Persistence.createEntityManagerFactory("descorp");
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            em.persist(c);
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
    
    private static Usuario createUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNome("Tiago Lima");
        usuario.setEmail("tiagolimadev@outlook.com");
        usuario.setLogin("tiagolima");
        usuario.setSenha("123");
        usuario.setCpf("123.456.789-10");
        usuario.setDataNascimento(Date.from(Instant.EPOCH));
        usuario.setTipo(TipoUsuario.ADMINISTRADOR);
        
        return usuario;
    }
}
