package com.algaworks.ecommerce.listener;

import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.service.NotaFiscalService;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class GerarNotaFiscal {

    private NotaFiscalService service = new NotaFiscalService();

    @PrePersist
    @PreUpdate
    public void gerar(final Pedido pedido) {
        if (pedido.isPago() && pedido.getNotafiscal() == null) {
            service.gerar(pedido);
        }
    }
}
