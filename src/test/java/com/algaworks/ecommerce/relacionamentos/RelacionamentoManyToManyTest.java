package com.algaworks.ecommerce.relacionamentos;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.Produto;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;


public class RelacionamentoManyToManyTest extends EntityManagerTest {

    @Test
    public void verificarRelacionamento() {
        var produto = entityManager.find(Produto.class, 1);
        var categoria = entityManager.find(Categoria.class, 1);

        entityManager.getTransaction().begin();
        produto.setCategorias(Arrays.asList(categoria));
        entityManager.getTransaction().commit();

        entityManager.clear();

        var categoriaNew = entityManager.find(Categoria.class, 1);
        assertTrue(!categoriaNew.getProdutos().isEmpty());
    }
}
