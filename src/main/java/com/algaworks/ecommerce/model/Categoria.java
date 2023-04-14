package com.algaworks.ecommerce.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "categoria", uniqueConstraints = { @UniqueConstraint(name = "unq_categoria", columnNames = {"nome"})},
        indexes = { @Index(name = "idx_categoria", columnList = "nome")}

)
public class Categoria {

    @EqualsAndHashCode.Include
    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    //@SequenceGenerator(name = "seq", sequenceName = "sequencias_chave_primaria")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 100, nullable = false)
    private Integer id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "catetoria_id", foreignKey = @ForeignKey(name = "fk_categoria_categoriapai"))
    private Categoria categoriaPai;

    @OneToMany(mappedBy = "categoriaPai")
    private List<Categoria> categorias;

    @ManyToMany(mappedBy = "categorias")
    private List<Produto> produtos;
}
