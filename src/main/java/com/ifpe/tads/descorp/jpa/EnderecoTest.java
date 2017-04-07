/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.jpa;

import com.ifpe.tads.descorp.model.endereco.Endereco;

/**
 *
 * @author Tiago Lima <tiagolimadev@outlook.com>
 */
public class EnderecoTest {
    public static void main(String[] args) {
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua Morada Nova");
        endereco.setCidade("Recife");
        endereco.setEstado("Pernambuco");
        endereco.setCep("12.345-678");
        endereco.setBairro("Passarinho");
        endereco.setComplemento("B");
        endereco.setNumero(38);
        
        endereco.inserirEndereco();
        
        Endereco endereco2 = Endereco.selecionarEndereco(1L);
        endereco2.setCidade("Olinda");
        
        endereco2.atualizarEndereco();
        
//        endereco2.removerEndereco();;
    }
}
