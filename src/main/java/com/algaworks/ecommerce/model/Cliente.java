package com.algaworks.ecommerce.model;

import com.algaworks.ecommerce.listener.GenericListener;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "cliente",
        uniqueConstraints = { @UniqueConstraint(name = "unq_cpf", columnNames = {"cpf"})},
        indexes = { @Index(name = "idx_cliente", columnList = "nome") }
)
@EntityListeners({ GenericListener.class })
@SecondaryTable(name = "cliente_detalhe", pkJoinColumns = @PrimaryKeyJoinColumn(name = "cliente_id"))
public class Cliente extends EntidadeBaseInteger {

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;

    @Transient
    private String primeiroNome;

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "cliente_id", foreignKey = @ForeignKey(name = "fk_cliente_contratos")))
    @MapKeyColumn(name = "tipo")
    @Column(name = "descricao")
    private Map<String, String> contatos;

    @Column(table = "cliente_detalhe", length = 30, nullable = false, name = "sexo")
    @Enumerated(EnumType.STRING)
    private SexoCliente sexo;

    @Column(table = "cliente_detalhe", name = "data_nascimento")
    private LocalDate dataAniversario;

    @Column(length = 14)
    private String cpf;

    @PostLoad
    public void configurarPrimeiroNome() {
        if (nome != null && !nome.isBlank()) {

            var index = nome.indexOf(" ");

            if (index >= 0) {
                this.primeiroNome = nome.substring(0, index);
            }
        }
    }
}
