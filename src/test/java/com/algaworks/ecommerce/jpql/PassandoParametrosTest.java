package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.NotaFiscal;
import org.junit.Test;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class PassandoParametrosTest extends EntityManagerTest {

    @Test
    public void passarParametroDate() {
        final var jpql = "select nf from NotaFiscal nf where nf.dataEmissao <= ?1";

        TypedQuery<NotaFiscal> typedQuery = entityManager.createQuery(jpql, NotaFiscal.class);
        typedQuery.setParameter(1, new Date(), TemporalType.TIMESTAMP);

        var result = typedQuery.getResultList();

        assertTrue(result.size() == 1);
    }
}
