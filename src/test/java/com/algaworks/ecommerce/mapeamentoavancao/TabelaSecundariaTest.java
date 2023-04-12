package com.algaworks.ecommerce.mapeamentoavancao;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.SexoCliente;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;

public class TabelaSecundariaTest extends EntityManagerTest {

    @Test
    public void testInsertTabelaSecundaria() {
        final Cliente cliente = new Cliente();
        cliente.setNome("fabricio");
        cliente.setDataAniversario(LocalDate.now());
        cliente.setSexo(SexoCliente.MASCULINO);

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();
        entityManager.clear();

        final Cliente check = entityManager.find(Cliente.class, cliente.getId());

        assertNotNull(check.getSexo());
    }
}
