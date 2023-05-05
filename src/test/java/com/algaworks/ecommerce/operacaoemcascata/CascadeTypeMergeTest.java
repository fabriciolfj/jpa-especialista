package com.algaworks.ecommerce.operacaoemcascata;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.*;
import jdk.jshell.Snippet;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class CascadeTypeMergeTest extends EntityManagerTest {

    @Test
    public void atualizarItemPedidoComPedido() {
        final Cliente cliente = entityManager.find(Cliente.class, 1);
        final Produto produto = entityManager.find(Produto.class, 1);

        final Pedido pedido = new Pedido();
        pedido.setId(2);
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.PAGO);

        final ItemPedido itemPedido = new ItemPedido();
        itemPedido.setItemPedidoId(new ItemPedidoId());
        itemPedido.getItemPedidoId().setPedidoId(pedido.getId());
        itemPedido.getItemPedidoId().setProdutoId(produto.getId());
        itemPedido.setPedido(pedido);
        itemPedido.setQuantidade(5);
        itemPedido.setPrecoProduto(produto.getPreco());

        pedido.setItemPedidos(List.of(itemPedido));

        entityManager.getTransaction().begin();
        entityManager.merge(itemPedido);
        entityManager.getTransaction().commit();

        entityManager.clear();

        final Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        assertTrue(pedidoVerificacao.getStatus().equals(StatusPedido.PAGO));
    }

    @Test
    public void atualizarPedidoComItens() {
        final Cliente cliente = entityManager.find(Cliente.class, 1);
        final Produto produto = entityManager.find(Produto.class, 1);

        final Pedido pedido = new Pedido();
        pedido.setId(2);
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setTotal(BigDecimal.ZERO);

        final ItemPedido itemPedido = new ItemPedido();
        itemPedido.setItemPedidoId(new ItemPedidoId());
        itemPedido.getItemPedidoId().setPedidoId(pedido.getId());
        itemPedido.getItemPedidoId().setProdutoId(produto.getId());
        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(3);
        itemPedido.setPrecoProduto(produto.getPreco());

        pedido.setItemPedidos(List.of(itemPedido));

        entityManager.getTransaction().begin();
        entityManager.merge(pedido);
        entityManager.getTransaction().commit();

        entityManager.clear();

        final ItemPedido itemPedidoVerificacao = entityManager.find(ItemPedido.class, itemPedido.getItemPedidoId());
        assertTrue(itemPedidoVerificacao.getQuantidade().equals(3));
    }
}
