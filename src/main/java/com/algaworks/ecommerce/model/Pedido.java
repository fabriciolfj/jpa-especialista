package com.algaworks.ecommerce.model;

import com.algaworks.ecommerce.listener.GenericListener;
import com.algaworks.ecommerce.listener.GerarNotaFiscal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@EntityListeners({ GerarNotaFiscal.class, GenericListener.class})
@Table(name = "pedido")
public class Pedido extends EntidadeBaseInteger {

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_ultima_atualizacao", insertable = false)
    private LocalDateTime dataUltimaAtualizacao;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pedido_cliente"))
    private Cliente cliente;

    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;

    @OneToOne(mappedBy = "pedido")
    private NotaFiscal notafiscal;

    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal total;

    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @Embedded
    private EnderecoEntregaPedido enderecoEntrega;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.REMOVE)
    private List<ItemPedido> itemPedidos;

    @OneToOne(mappedBy = "pedido")
    private Pagamento pagamento;

    public boolean isPago() {
        return this.status == StatusPedido.PAGO;
    }

    public void calcularTotal() {
        if (itemPedidos != null) {
            this.total = itemPedidos
                    .stream()
                    .map(item -> item.getPrecoProduto().multiply(new BigDecimal(item.getQuantidade())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                    //.reduce(BigDecimal.ZERO, (x, y) -> x.add(y));
            return;
        }

        total = BigDecimal.ZERO;
    }

    @PrePersist
    public void aoPersistir() {
        dataCriacao = LocalDateTime.now();
        calcularTotal();
    }

    @PreUpdate
    public void aoAtualizar() {
        dataUltimaAtualizacao = LocalDateTime.now();
        calcularTotal();
    }
}
