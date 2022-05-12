package com.algaworks.ecommerce.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "nota_fiscal")
public class NotaFiscal {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "pedido_id")
    private Integer id;

    @MapsId// Para ter uma notafiscal precisa OBRIGATORIAMENTE de um pedido
    @OneToOne(optional = false) // logo faz sentido colocar o id do pedido como PK da Nota Fiscal
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    private String xml;

    @Column(name = "data_emissao")
    private Date dataEmissao;
}
