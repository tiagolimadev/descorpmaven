///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.ifpe.tads.descorp.jpa;
//
//import com.ifpe.tads.descorp.model.endereco.Endereco;
//import com.ifpe.tads.descorp.model.fornecedor.Fornecedor;
//import java.util.ArrayList;
//
///**
// *
// * @author Eduardo
// */
//public class FornecedorTest {
//    
//    public static void main(String[] args) {
//        
//        Fornecedor forc = new Fornecedor();
//        
//        forc.setDescricao("Teste");
//        forc.setNome("Teste");
//        forc.setEnderecos(new ArrayList<Endereco>());
//        
//        Endereco end = new Endereco();
//        end.setBairro("B");
//        end.setCep("123123");
//        end.setCidade("C");
//        end.setComplemento("AMM");
//        end.setEstado("E");
//        end.setFornecedores(new ArrayList<Fornecedor>());
//        end.getFornecedores().add(forc);
//        
//        forc.inserirFornecedor();
//        
//        end.atualizarEndereco();
//        
//        forc = Fornecedor.selecionarFornecedor(1L);
//        
//        forc.setNome("FORC");
//        
//        forc.atualizarFornecedor();
//        
//        forc = Fornecedor.selecionarFornecedor(1L);
//        
//        //forc.removerFornecedor();
//        
//    }
//    
//}
