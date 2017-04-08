/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.jpa;

import com.ifpe.tads.descorp.model.produto.Produto;
import com.ifpe.tads.descorp.model.usuario.Cliente;
import com.ifpe.tads.descorp.model.usuario.Operador;
import com.ifpe.tads.descorp.model.usuario.TipoUsuario;
import com.ifpe.tads.descorp.model.venda.ItemVenda;
import com.ifpe.tads.descorp.model.venda.Venda;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Eduardo
 */
public class VendaTest {
 
    public static void main(String[] args) {
        mock();
        
        Venda venda = new Venda();
        
        venda.setItensVenda(
                new ArrayList<ItemVenda>());
        venda.setDataVenda(new Date());
        
        ItemVenda iv = new ItemVenda();
        iv.setVenda(venda);
        
        Produto prod = Produto.selecionarProduto(1L);
        
        iv.setProduto(prod);
        iv.setPrecoUnitario(iv.getProduto().getPreco());
        iv.setQuantidade(10);
        
        venda.getItensVenda().add(iv);
        
        venda.setOperador(Operador.selecionarOperador(1L));
        venda.setCliente(Cliente.selecionarCliente(1L));
        
        venda.inserirVenda();
        
        venda = Venda.selecionarVenda(1L);
        
        ItemVenda iv2 = new ItemVenda();
        iv2.setVenda(venda);
        
        Produto prod2 = Produto.selecionarProduto(2L);
        
        iv2.setProduto(prod2);
        iv2.setPrecoUnitario(prod2.getPreco());
        iv2.setQuantidade(10);
        
        venda.getItensVenda().add(iv2);
        
        venda.atualizarVenda();
        
        ItemVenda item = ItemVenda.selecionarItemVenda(2L);
        
        item.setQuantidade(30);
        
        item.atualizarItemVenda();
        
        ItemVenda item1 = ItemVenda.selecionarItemVenda(1L);
        
        item1.removerItemVenda();
        
        //venda.removerVenda();
        
    }
    
    private static void mock(){
        Produto prod = new Produto();
        prod.setDescricao("TESTE 1");
        prod.setNome("TESTE");
        prod.setPreco(5.0);
        
        prod.inserirProduto();
        
        Produto prod2 = new Produto();
        prod2.setDescricao("TESTE 2");
        prod2.setNome("TESTE 2");
        prod2.setPreco(10.0);
        
        prod2.inserirProduto();
        
        Cliente cliente = new Cliente();
        
        cliente.setCpf("123");
        cliente.setDataNascimento(new GregorianCalendar(1992, Calendar.APRIL, 8).getTime());
        cliente.setEmail("EML");
        cliente.setLogin("sad");
        cliente.setSenha("123");
        cliente.setTipo(TipoUsuario.CLIENTE);
        
        cliente.inserirUsuario();
        
        Operador operador = new Operador();
        
        operador.setCpf("123");
        operador.setDataNascimento(new GregorianCalendar(1993, Calendar.MAY, 15).getTime());
        operador.setEmail("EML");
        operador.setLogin("sad");
        operador.setSenha("123");
        operador.setTipo(TipoUsuario.OPERADOR);
        
        operador.inserirUsuario();
        
    }
    
}
