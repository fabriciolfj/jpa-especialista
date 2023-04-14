package com.algaworks.ecommerce.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "estoque")
public class Estoque {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    @OneToOne(optional = false)
    @JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(name = "fk_estoque_produto"))
    private Produto produto;

    @Column(columnDefinition = "int(11) not null")
    private Integer quantidade;
}
