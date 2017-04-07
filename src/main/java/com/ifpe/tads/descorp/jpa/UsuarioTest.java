/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.jpa;

import com.ifpe.tads.descorp.model.usuario.Administrador;
import com.ifpe.tads.descorp.model.usuario.Cliente;
import com.ifpe.tads.descorp.model.usuario.Operador;
import com.ifpe.tads.descorp.model.usuario.TipoUsuario;
import java.util.Date;

/**
 *
 * @author Maurício
 */
public class UsuarioTest {

    public static void main(String[] args) {
        testeCliente();
        testeOperador();
        testeAdministrador();
    }
    
    private static void testeCliente(){
        Cliente cliente = new Cliente();
        
        cliente.setCpf("123");
        cliente.setDataNascimento(new Date(1990, 2, 1));
        cliente.setEmail("EML");
        cliente.setLogin("sad");
        cliente.setSenha("123");
        cliente.setTipo(TipoUsuario.CLIENTE);
        
        cliente.inserirUsuario();
        
        Cliente cl2 = Cliente.selecionarCliente(1L);
        
        cl2.setNome("JUVINALDO");
        
        cl2.atualizarusuario();
        
        Cliente cl3 = Cliente.selecionarCliente(1L);
        
       // cl3.removerUsuario();
    }
    
    private static void testeOperador(){
        Operador operador = new Operador();
        
        operador.setCpf("123567");
        operador.setDataNascimento(new Date(1983, 9, 14));
        operador.setEmail("EMLAAA");
        operador.setLogin("AAA");
        operador.setSenha("123456");
        operador.setTipo(TipoUsuario.OPERADOR);
        
        operador.inserirUsuario();
        
        Operador op2 = Operador.selecionarOperador(2L);
        
        op2.setEmail("AMMMM@MMMMM.COM");
        
        op2.atualizarusuario();
        
        Operador op3 = Operador.selecionarOperador(2L);
        
       // op3.removerUsuario();
    }
    
    private static void testeAdministrador(){
        Administrador administrador = new Administrador();
        
        administrador.setCpf("123567");
        administrador.setDataNascimento(new Date(1983, 9, 14));
        administrador.setEmail("EMLAAA");
        administrador.setLogin("AAA");
        administrador.setSenha("123456");
        administrador.setTipo(TipoUsuario.ADMINISTRADOR);
        
        administrador.inserirUsuario();
        
        Administrador adm2 = Administrador.selecionarAdministrador(3L);
        
        adm2.setDataNascimento(new Date(2001, 3, 27));
        
        adm2.atualizarusuario();
        
        Administrador adm3 = Administrador.selecionarAdministrador(3L);
        
       // adm3.removerUsuario();
    }
    
}
