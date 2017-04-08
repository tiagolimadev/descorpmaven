/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.jpa;

import com.ifpe.tads.descorp.entrega.Entrega;
import com.ifpe.tads.descorp.entrega.StatusEntrega;
import com.ifpe.tads.descorp.model.produto.Categoria;
import com.ifpe.tads.descorp.model.produto.Produto;
import com.ifpe.tads.descorp.model.usuario.Cliente;
import com.ifpe.tads.descorp.model.usuario.Entregador;
import com.ifpe.tads.descorp.model.usuario.Usuario;
import com.ifpe.tads.descorp.model.venda.ItemVenda;
import com.ifpe.tads.descorp.model.venda.Venda;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Tiago Lima <tiagolimadev@outlook.com>
 */
public class EntregaTest {
    public static void main(String[] args) {
        mock();
        
        Entrega entrega = new Entrega();
        entrega.setDataEntrega(new GregorianCalendar(2017, Calendar.APRIL, 8).getTime());
        entrega.setStatusEntrega(StatusEntrega.ESPERA);
        
        entrega.inserirEntrega();
        
        Entrega entrega2 = Entrega.selecionarEntrega(1L);
        entrega2.setEntregador((Entregador) Usuario.selecionarUsuario(2L));
        entrega2.setStatusEntrega(StatusEntrega.ENVIADA);
        
        entrega2.atualizarEntrega();
        
//        entrega2.removerEntrega();
    }

    private static void mock() {
        List<Produto> produtos = new ArrayList();
        List<ItemVenda> itensVenda = new ArrayList();
        
        Usuario cliente = new Cliente();
        cliente.setNome("Marcos Costa");
        cliente.setCpf("111.222.333-44");
        cliente.inserirUsuario();
        
        Produto produto = new Produto();
        produto.setNome("Moto G5 Plus");
        produto.setPreco(1499.99);
        produto.setCodigo("MG5P");
        produto.inserirProduto();
        
        produtos.add(produto);
        
        Categoria categoria = new Categoria();
        categoria.setNome("celulares");
        categoria.setProdutos(produtos);
        categoria.inserirCategoria();
        
        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setPrecoUnitario(produto.getPreco());
        itemVenda.setQuantidade(2);
        itemVenda.setProduto(produto);
        itemVenda.inserirItemVenda();
        
        itensVenda.add(itemVenda);
        
        Venda venda = new Venda();
        venda.setDataVenda(new GregorianCalendar(2017, Calendar.APRIL, 8).getTime());
        venda.setItensVenda(itensVenda);
        venda.setCliente((Cliente) Usuario.selecionarUsuario(1L));
        venda.inserirVenda();
                
        Usuario entregador = new Entregador();
        entregador.setNome("Eduardo Amaral");
        entregador.setCpf("987.654.321-00");
        entregador.inserirUsuario();
    }
}
