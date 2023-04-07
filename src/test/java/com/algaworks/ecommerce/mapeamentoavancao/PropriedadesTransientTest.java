package com.algaworks.ecommerce.mapeamentoavancao;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PropriedadesTransientTest extends EntityManagerTest {

    @Test
    public void testPrimeiroNome() {
        final Cliente cliente = entityManager.find(Cliente.class, 1);

        assertEquals(cliente.getPrimeiroNome(), "Fernando");
    }
}
