/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.jpa;

import com.ifpe.tads.descorp.model.compra.Compra;
import com.ifpe.tads.descorp.model.compra.ItemCompra;
import com.ifpe.tads.descorp.model.fornecedor.Fornecedor;
import com.ifpe.tads.descorp.model.produto.Produto;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Eduardo
 */
public class ComprasJPA {
    
    public static void main(String[] args) {
        mock();
        
        Compra compra = new Compra();
        
        compra.setItensCompra(new ArrayList<ItemCompra>());
        compra.setDataCompra(new Date());
        
        ItemCompra ic = new ItemCompra();
        ic.setCompra(compra);
        
        Produto prod = Produto.selecionarProduto(1L);
        
        ic.setProduto(prod);
        ic.setPrecoUnitario(ic.getProduto().getPreco());
        ic.setQuantidade(10);
        
        compra.getItensCompra().add(ic);
        
        compra.setFornecedor(Fornecedor.selecionarFornecedor(1L));
        
        compra.inserirCompra();
        
        compra = Compra.selecionarCompra(1L);
        
        ItemCompra ic2 = new ItemCompra();
        ic2.setCompra(compra);
        
        Produto prod2 = Produto.selecionarProduto(2L);
        
        ic2.setProduto(prod2);
        ic2.setPrecoUnitario(prod2.getPreco());
        ic2.setQuantidade(10);
        
        compra.getItensCompra().add(ic2);
        
        compra.atualizarCompra();
        
        compra.removerCompra();
        
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
        
        Fornecedor f = new Fornecedor();
        
        f.setDescricao("DESC");
        f.setNome("NOME");
        
        f.inserirFornecedor();
    }
    
}
