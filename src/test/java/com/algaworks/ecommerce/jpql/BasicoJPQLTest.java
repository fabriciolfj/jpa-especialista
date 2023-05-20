package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import org.junit.Test;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BasicoJPQLTest extends EntityManagerTest {

    @Test
    public void buscaPorIdentificador() {
        final TypedQuery<Pedido> typedQuery = entityManager
                .createQuery("select p from Pedido p where p.id = 7", Pedido.class);

        final Pedido pedido = typedQuery.getSingleResult();
        assertNotNull(pedido);
    }

    @Test
    public void mostrarDiferencaQueryTypedQuery() {
        final var jpql = "select p from Pedido p where p.id = 7";

        final TypedQuery<Pedido> typed = entityManager.createQuery(jpql, Pedido.class);
        final var result = typed.getSingleResult();

        assertNotNull(result);

        final Query query = entityManager.createQuery(jpql);

        final Pedido pedido = (Pedido) query.getSingleResult();
        assertNotNull(pedido);
    }

    @Test
    public void selectionarUmAtributoParaRetorno() {
        final var jqpl = "select p.nome from Produto p";

        final TypedQuery<String> query = entityManager.createQuery(jqpl, String.class);
        var names = query.getResultList();

        assertTrue(names.size() > 1);

        final TypedQuery<Cliente> queryClient = entityManager.createQuery("select p.cliente from Pedido p where p.id = 7", Cliente.class);
        final var cliente = queryClient.getSingleResult();

        assertNotNull(cliente);
        assertTrue(Cliente.class.equals(cliente.getClass()));

    }
}
