package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;
import org.junit.Test;

import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OperacoesEmLoteTest extends EntityManagerTest {

    private static final int LIMITE_INSERCOES = 4;

    @Test
    public void atualizarEmLote() {
        entityManager.getTransaction().begin();

        var jpql = "update Produto p set p.preco = p.preco + 1 where exists (select 1 from p.categorias c2 where c2.id = :categoria)";

        Query query = entityManager.createQuery(jpql);
        query.setParameter("categoria", 3);
        query.executeUpdate();

        entityManager.getTransaction().commit();
    }

    @Test
    public  void inserirEmLote() {
        var in = OperacoesEmLoteTest.class.getClassLoader()
                        .getResourceAsStream("produtos/importar.txt");
        var reader = new BufferedReader(new InputStreamReader(in));

        entityManager.getTransaction().begin();
        var contador = 0;

        for(String linha: reader.lines().toList()) {
            if (linha == null) {
                continue;
            }

            var results = linha.split(";");
            var produto = new Produto();
            produto.setNome(results[0]);
            produto.setDescricao(results[1]);
            produto.setPreco(new BigDecimal(results[2]));
            produto.setDataCriacao(LocalDateTime.now());

            entityManager.persist(produto);

            if (++contador == LIMITE_INSERCOES) {
                entityManager.flush();
                entityManager.clear();

                contador = 0;

                System.out.println("---------------------");
            }
        }

        entityManager.getTransaction().commit();
    }
}
