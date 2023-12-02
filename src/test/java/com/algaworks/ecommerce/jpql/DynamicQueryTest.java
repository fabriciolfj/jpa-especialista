package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DynamicQueryTest extends EntityManagerTest {

    @Test
    public void testDynamicQuery() {
        var produto = new Produto();
        produto.setNome("K");

        var result = getProdutos(produto);
        Assert.assertFalse(result.isEmpty());
    }

    public List<Produto> getProdutos(final Produto produto) {
        final StringBuilder sb = new StringBuilder();
        sb.append("select p from Produto p where 1=1 ");

        if (produto.getNome() != null) {
            sb.append("and p.nome like concat('%', :nome ,'%')");
        }

        if (produto.getDescricao() != null) {
            sb.append("and p.descricao like concat('%', :descricao ,'%')");
        }

        var query = this.entityManager.createQuery(sb.toString(), Produto.class);

        if (produto.getNome() != null) {
            query.setParameter("nome", produto.getNome());
        }

        if (produto.getDescricao() != null) {
            query.setParameter("descricao", produto.getDescricao());
        }

        return query.getResultList();
    }
}
