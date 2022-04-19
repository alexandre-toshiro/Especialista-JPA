package com.algaworks.ecommerce.model;

import com.algaworks.ecommerce.listener.GenericoListener;
import com.algaworks.ecommerce.listener.GerarNotaFiscalListener;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners({GerarNotaFiscalListener.class, GenericoListener.class})
@Entity
@Table(name = "pedido")
public class Pedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    // quando não se tem o JoinColumn, ele adota por padrão uma nomeclatura do nome dessa váriavel
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;// mais o nome da PK da entidade Cliente ficaria cliente_id

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_ultima_atualizacao")
    private LocalDateTime dataUltimaAtualizacao;

    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;

    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @Embedded
    private EnderecoEntregaPedido enderecoEntrega;

    @OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER)// o padrão é lazy
    private List<ItemPedido> itens;

    @OneToOne(mappedBy = "pedido")
    private PagamentoCartao pagamento;

    @OneToOne(mappedBy = "pedido")
    private NotaFiscal notaFiscal;

    // Métodos de call back mais utilizados
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

    // Outro métodos de call back

    @PostPersist
    public void aposPersistir() {
        System.out.println("Após persistir um Pedido");
    }

    @PostUpdate
    public void aposAtualizar() {
        System.out.println("Após atualizar o Pedido");
    }

    @PreRemove
    public void aoRemover() {
        System.out.println("Após remover");
    }

    @PostRemove
    public void aposRemover() {
        System.out.println("Após remover Pedido");
    }

    @PostLoad
    public void aoCarregar() {
        System.out.println("Após carregar o pedido");
    }

    // Podemos ter um mesmo método anotado com varias anotações
//    @PrePersist comentado pois só podemos ter um prepersist por objeto
//    @PreUpdate
    public void calcularTotal() {
        if (itens != null) {
            total = itens.stream().map(ItemPedido::getPrecoProduto)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }

    public boolean isPago() {
        return StatusPedido.PAGO.equals(status);
    }
}
