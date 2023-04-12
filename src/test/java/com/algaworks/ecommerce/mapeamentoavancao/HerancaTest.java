package com.algaworks.ecommerce.mapeamentoavancao;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.*;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class HerancaTest extends EntityManagerTest {

    @Test
    public void testChaveMappedSuperClass() {
        final Cliente cliente = new Cliente();
        cliente.setNome("fabricio");

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();
        entityManager.clear();

        final Cliente check = entityManager.find(Cliente.class, cliente.getId());
        assertNotNull(check.getId());
    }

    @Test
    public void testBuscaPagamentos() {
        final List<Pagamento> pagamentos = entityManager.createQuery("select e from Pagamento e")
                .getResultList();

        assertNotNull(pagamentos);
        assertTrue(pagamentos.size() > 0);
    }

    @Test
    public void testAddPagamentoNoPedido() {
        final Pedido pedido = entityManager.find(Pedido.class, 1);

        final PagamentoCartao cartao = new PagamentoCartao();
        cartao.setNumero("123");
        cartao.setPedido(pedido);

        entityManager.getTransaction().begin();
        entityManager.persist(cartao);
        entityManager.getTransaction().commit();
        entityManager.clear();

        final Pedido check = entityManager.find(Pedido.class, pedido.getId());
        assertNotNull(check.getPagamento());
    }
}
