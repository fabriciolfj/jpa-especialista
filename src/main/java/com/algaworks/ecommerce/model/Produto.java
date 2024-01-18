package com.algaworks.ecommerce.model;

import com.algaworks.ecommerce.listener.GenericListener;
import com.algaworks.ecommerce.listener.GerarNotaFiscal;
import com.algaworks.ecommerce.model.converter.BooleanToSimNaoConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@NamedQueries({
        @NamedQuery(name = "Produto.lista", query = "select p from Produto p"),
        @NamedQuery(name = "Produto.listaCategoria", query = "select p from Produto p where exists (select 1 from p.categorias c where c.id = :categoria)")
})
@Table(name = "produto",
        indexes = { @Index(name = "idx_produto", columnList = "nome")}//,
        //uniqueConstraints = { @UniqueConstraint(name = "unq_produto", columnNames = { "nome "})}
)
@EntityListeners({ GenericListener.class})
public class Produto extends EntidadeBaseInteger {

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_ultima_atualizacao", insertable = false)
    private LocalDateTime dataUltimaAtualizacao;

    @Column(length = 100, nullable = false)
    private String nome;

    @Lob
    private String descricao;

    private BigDecimal preco;

    @ManyToMany
    @JoinTable(name = "produto_categoria",
            joinColumns =
                @JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(name = "fk_produto_categoria_produto")),
            inverseJoinColumns =
                @JoinColumn(name = "categoria_id", nullable = false, foreignKey = @ForeignKey(name = "fk_produto_categoria_categoria"))
    )
    private List<Categoria> categorias;

    @OneToOne(mappedBy = "produto")
    private Estoque estoque;

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "produto_id", foreignKey = @ForeignKey(name = "fk_produto_tags")), name = "produto_tag")
    @Column(name = "tag", length = 50, nullable = false)
    private List<String> tags;

    @ElementCollection
    @CollectionTable(name = "produto_atributo", joinColumns = @JoinColumn(name = "produto_id"))
    private List<Atributo> atributos;

    @Lob
    private byte[] foto;

    @Column(name = "ativo", length = 3)
    @Convert(converter = BooleanToSimNaoConverter.class)
    private Boolean ativo = Boolean.TRUE;

}
