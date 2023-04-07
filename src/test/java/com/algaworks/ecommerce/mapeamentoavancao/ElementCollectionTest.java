package com.algaworks.ecommerce.mapeamentoavancao;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.Produto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ElementCollectionTest extends EntityManagerTest {

    @Test
    public void testInserElementCollection() {
        final Categoria categoria = entityManager.find(Categoria.class, 1);

        var produto = new Produto();
        produto.setCategorias(List.of(categoria));
        produto.setPreco(BigDecimal.TEN);
        produto.setDescricao("test");
        produto.setTags(List.of("test1", "test2"));

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        final Produto newProduto = entityManager.find(Produto.class, produto.getId());
        assertTrue(newProduto.getTags().size() == 2);
    }
}
