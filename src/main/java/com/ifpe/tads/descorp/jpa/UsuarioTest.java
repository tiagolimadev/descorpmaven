///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.ifpe.tads.descorp.jpa;
//
//import com.ifpe.tads.descorp.model.usuario.Administrador;
//import com.ifpe.tads.descorp.model.usuario.Cliente;
//import com.ifpe.tads.descorp.model.usuario.Operador;
//import com.ifpe.tads.descorp.model.usuario.TipoUsuario;
//import java.util.Calendar;
//import java.util.GregorianCalendar;
//
///**
// *
// * @author Maurício
// */
//public class UsuarioTest {
//
//    public static void main(String[] args) {
//        testeCliente();
//        testeOperador();
//        testeAdministrador();
//    }
//    
//    private static void testeCliente(){
//        Cliente cliente = new Cliente();
//        
//        cliente.setCpf("123");
//        cliente.setDataNascimento(new GregorianCalendar(1990, Calendar.APRIL, 1).getTime());
//        cliente.setEmail("EML");
//        cliente.setLogin("sad");
//        cliente.setSenha("123");
//        cliente.setTipo(TipoUsuario.CLIENTE);
//        
//        cliente.inserirUsuario();
//        
//        Cliente cl2 = Cliente.selecionarCliente(1L);
//        
//        cl2.setNome("JUVINALDO");
//        
//        cl2.atualizarUsuario();
//        
//        Cliente cl3 = Cliente.selecionarCliente(1L);
//        
//       cl3.removerUsuario();
//    }
//    
//    private static void testeOperador(){
//        Operador operador = new Operador();
//        
//        operador.setCpf("123567");
//        operador.setDataNascimento(new GregorianCalendar(1984, Calendar.JULY, 18).getTime());
//        operador.setEmail("EMLAAA");
//        operador.setLogin("AAA");
//        operador.setSenha("123456");
//        operador.setTipo(TipoUsuario.OPERADOR);
//        
//        operador.inserirUsuario();
//        
//        Operador op2 = Operador.selecionarOperador(2L);
//        
//        op2.setEmail("AMMMM@MMMMM.COM");
//        
//        op2.atualizarUsuario();
//        
//        Operador op3 = Operador.selecionarOperador(2L);
//        
//        op3.removerUsuario();
//    }
//    
//    private static void testeAdministrador(){
//        Administrador administrador = new Administrador();
//        
//        administrador.setCpf("123567");
//        administrador.setDataNascimento(new GregorianCalendar(2000, Calendar.DECEMBER, 25).getTime());
//        administrador.setEmail("EMLAAA");
//        administrador.setLogin("AAA");
//        administrador.setSenha("123456");
//        administrador.setTipo(TipoUsuario.ADMINISTRADOR);
//        
//        administrador.inserirUsuario();
//        
//        Administrador adm2 = Administrador.selecionarAdministrador(3L);
//        
//        adm2.setDataNascimento(new GregorianCalendar(1994, Calendar.JANUARY, 27).getTime());
//        
//        adm2.atualizarUsuario();
//        
//        Administrador adm3 = Administrador.selecionarAdministrador(3L);
//        
//        adm3.removerUsuario();
//    }
//    
//}
