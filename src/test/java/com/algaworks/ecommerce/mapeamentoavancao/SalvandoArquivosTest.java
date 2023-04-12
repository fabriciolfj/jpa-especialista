package com.algaworks.ecommerce.mapeamentoavancao;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SalvandoArquivosTest extends EntityManagerTest {

    @Test
    public void testSalvandoFotoProduto() {
        final var produto = new Produto();
        produto.setPreco(BigDecimal.TEN);
        produto.setDescricao("teste");
        produto.setFoto(getFoto());

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();
        entityManager.clear();

        final Produto check = entityManager.find(Produto.class, produto.getId());

        assertNotNull(check.getFoto());
        assertTrue(check.getFoto().length > 0);
    }

    private byte[] getFoto() {
        try {
            return SalvandoArquivosTest.class.getResourceAsStream("/produto.png")
                    .readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
