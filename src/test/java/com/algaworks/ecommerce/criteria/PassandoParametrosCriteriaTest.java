package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.NotaFiscal;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import java.util.Calendar;
import java.util.Date;

public class PassandoParametrosCriteriaTest extends EntityManagerTest {

    @Test
    public void passarParametroDate() {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<NotaFiscal> query = builder.createQuery(NotaFiscal.class);
        final var root = query.from(NotaFiscal.class);

        query.select(root);
        final ParameterExpression<Date> parameterExpressionDate = builder
                .parameter(Date.class, "dataInicial");

        query.where(builder.greaterThan(root.get("dataEmissao"),parameterExpressionDate));

        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -30);

        final var type = entityManager.createQuery(query);
        type.setParameter("dataInicial", calendar.getTime(), TemporalType.TIMESTAMP);

        var list = type.getResultList();
        Assert.assertFalse(list.isEmpty());
    }
}
