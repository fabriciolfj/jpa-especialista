package com.algaworks.ecommerce.mapeamentoavancao;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Atributo;
import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Produto;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;
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

    @Test
    public void testInserElementCollectionAtributo() {
        final Categoria categoria = entityManager.find(Categoria.class, 1);

        var produto = new Produto();
        produto.setCategorias(List.of(categoria));
        produto.setPreco(BigDecimal.TEN);
        produto.setDescricao("test");
        produto.setAtributos(List.of(new Atributo("teste", "teste"), new Atributo("teste", "teste")));

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        final Produto newProduto = entityManager.find(Produto.class, produto.getId());
        assertTrue(newProduto.getAtributos().size() == 2);
    }

    @Test
    public void testElementCollectionMap() {
        final Cliente cliente = entityManager.find(Cliente.class, 1);

        entityManager.getTransaction().begin();

        cliente.setContatos(Collections.singletonMap("email", "fernando@gmail.com"));

        entityManager.getTransaction().commit();

        entityManager.clear();

        final Cliente clienteContrato = entityManager.find(Cliente.class, 1);

        assertTrue(clienteContrato.getContatos().size() > 0);
    }
}
