package com.algaworks.ecommerce.relacionamentos;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class AutoRelacionamentoTest extends EntityManagerTest {

    @Test
    public void verificarRelacionamento() {
        Categoria pai = new Categoria();
        pai.setNome("Eletrônicos");

        Categoria categoria =  new Categoria();
        categoria.setNome("celulares");
        categoria.setCategoriaPai(pai);

        entityManager.getTransaction().begin();
        entityManager.persist(pai);
        entityManager.persist(categoria);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Categoria categoriaVerificacao = entityManager.find(Categoria.class, pai.getId());

        assertNotNull(categoriaVerificacao);
        assertFalse(categoriaVerificacao.getCategorias().isEmpty());
    }
}
