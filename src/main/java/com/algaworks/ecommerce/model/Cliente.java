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
@Table(name = "cliente")
@EntityListeners({ GenericListener.class })
@SecondaryTable(name = "cliente_detalhes", pkJoinColumns = @PrimaryKeyJoinColumn(name = "cliente_id"))
public class Cliente extends EntidadeBaseInteger {

    private String nome;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;

    @Transient
    private String primeiroNome;

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "cliente_id"))
    @MapKeyColumn(name = "tipo")
    @Column(name = "descricao")
    private Map<String, String> contatos;

    @Column(table = "cliente_detalhes")
    @Enumerated(EnumType.STRING)
    private SexoCliente sexo;
    @Column(table = "cliente_detalhes", name = "data_aniversario")
    private LocalDate dataAniversario;

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
