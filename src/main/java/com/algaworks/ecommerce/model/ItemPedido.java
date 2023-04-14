package com.algaworks.ecommerce.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "item_pedido")
//@IdClass(ItemPedidoId.class)
public class ItemPedido {

    @EmbeddedId
    private ItemPedidoId itemPedidoId;

    @MapsId("pedidoId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "pedido_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_item_pedido"), nullable = false)
    private Pedido pedido;

    @MapsId("produtoId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_item_produto"), nullable = false)
    private Produto produto;

    @Column(name = "preco_produto", precision = 19, scale = 2, nullable = false)
    private BigDecimal precoProduto;

    @Column(columnDefinition = "int(11) not null")
    private Integer quantidade;
}
