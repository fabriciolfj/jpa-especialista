package com.algaworks.ecommerce.relacionamentos;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.NotaFiscal;
import com.algaworks.ecommerce.model.PagamentoCartao;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.StatusPagamento;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertNotNull;


public class RelacionamentoOneToOneTest extends EntityManagerTest {

    @Test
    public void verificarRelacionamento() {
        var pedido = entityManager.find(Pedido.class, 1);

        var pgto = new PagamentoCartao();
        pgto.setNumero("1221");
        pgto.setStatus(StatusPagamento.PROCESSANDO);
        pgto.setPedido(pedido);

        entityManager.getTransaction().begin();
        entityManager.persist(pgto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        var pedidoCheck = entityManager.find(Pedido.class, 1);

        assertNotNull(pedidoCheck.getPagamento());
    }

    @Test
    public void verificarRelacionamentoPedidoNf() {
        var pedido = entityManager.find(Pedido.class, 1);

        var nota = new NotaFiscal();
        nota.setPedido(pedido);
        nota.setXml("Xml");
        nota.setDataEmissao(new Date());

        entityManager.getTransaction().begin();
        entityManager.persist(nota);
        entityManager.getTransaction().commit();

        entityManager.clear();

        var pedidoCheck = entityManager.find(Pedido.class, 1);

        assertNotNull(pedidoCheck.getNotafiscal());
    }
}
