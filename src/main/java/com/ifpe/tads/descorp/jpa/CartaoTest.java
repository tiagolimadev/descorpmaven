/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.jpa;

import com.ifpe.tads.descorp.model.cartao.Bandeira;
import com.ifpe.tads.descorp.model.cartao.CartaoDeCredito;
import com.ifpe.tads.descorp.model.endereco.Endereco;
import com.ifpe.tads.descorp.model.usuario.Cliente;
import com.ifpe.tads.descorp.model.usuario.TipoUsuario;
import com.ifpe.tads.descorp.model.usuario.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tiago Lima <tiagolimadev@outlook.com>
 */
public class CartaoTest {
    public static void main(String[] args) {
        mock();
        
        CartaoDeCredito cartao = new CartaoDeCredito();
        cartao.setCliente((Cliente) Usuario.selecionarUsuario(1L));
        cartao.setBandeira(Bandeira.VISA);
        
        cartao.inserirCartao();
        
        CartaoDeCredito cartao2 = CartaoDeCredito.selecionarCartao(1L);
        cartao2.setNome("Tiago R Lima");
        
        cartao2.atualizarCartao();
        
//        cartao2.removerCartao();
    }

    private static void mock() {
        List<Endereco> enderecos = new ArrayList();
        Endereco endereco1 = new Endereco();
        endereco1.setLogradouro("Rua Morada Nova");
        endereco1.inserirEndereco();
        enderecos.add(endereco1);
        
        Endereco endereco2 = new Endereco();
        endereco2.setLogradouro("Rua Cairo");
        endereco2.inserirEndereco();
        enderecos.add(endereco2);
        
        Usuario cliente = new Cliente();
        cliente.setTipo(TipoUsuario.CLIENTE);
        cliente.setCpf("123.456.789-10");
        cliente.setNome("Tiago Lima");
        cliente.setEnderecos(enderecos);
        cliente.inserirUsuario();
        
        
        List<Usuario> usuarios = new ArrayList<Usuario>();
        usuarios.add(cliente);
        
        endereco1.setUsuarios(usuarios);
        endereco2.setUsuarios(usuarios);
        
        endereco1.atualizarEndereco();
        endereco2.atualizarEndereco();
        
    }
            
}
