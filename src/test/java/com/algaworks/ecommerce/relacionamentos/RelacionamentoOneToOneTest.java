package com.algaworks.ecommerce.relacionamentos;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.NotaFiscal;
import com.algaworks.ecommerce.model.PagamentoCartao;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.StatusPagamento;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class RelacionamentoOneToOneTest extends EntityManagerTest {

    @Test
    public void verificarRelacionamento() {
        var pedido = entityManager.find(Pedido.class, 1);

        var pgto = new PagamentoCartao();
        pgto.setNumero("1221");
        pgto.setStatus(StatusPagamento.PROCESSANDO);
        pgto.setPedido(pedido);

        entityManager.getTransaction().begin();
        entityManager.persist(pgto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        var pedidoCheck = entityManager.find(Pedido.class, 1);

        assertNotNull(pedidoCheck.getPagamento());
    }

    @Test
    public void verificarRelacionamentoPedidoNf() {
        var pedido = entityManager.find(Pedido.class, 1);

        var nota = new NotaFiscal();
        nota.setPedido(pedido);
        nota.setXml(getXml());
        nota.setDataEmissao(new Date());

        entityManager.getTransaction().begin();
        entityManager.persist(nota);
        entityManager.getTransaction().commit();

        entityManager.clear();

        var pedidoCheck = entityManager.find(Pedido.class, 1);
        var nf = entityManager.find(NotaFiscal.class, nota.getId());

        assertNotNull(pedidoCheck.getNotafiscal());
        assertNotNull(nf.getXml());
        assertTrue(nf.getXml().length > 0);

        try {
            OutputStream out = new FileOutputStream(
                    Files.createFile(Paths.get(System.getProperty("user.home") + "/xml.xml")).toFile());
            out.write(nf.getXml());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private byte[] getXml() {
        try {
            return RelacionamentoOneToOneTest.class.getResourceAsStream(
                    "/nota-fiscal.xml").readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
