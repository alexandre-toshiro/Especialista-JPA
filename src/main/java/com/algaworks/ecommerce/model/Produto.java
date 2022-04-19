package com.algaworks.ecommerce.model;

import com.algaworks.ecommerce.listener.GenericoListener;
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
@EntityListeners({GenericoListener.class})
@Entity
@Table(name = "produto")
public class Produto {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String descricao;
    private BigDecimal preco;

    @ManyToMany// produto é o owner
    @JoinTable(name = "produto_categoria",// indica a tabela que foi criada para lidar com relacionamentos M:N
            joinColumns = @JoinColumn(name = "produto_id"),  // a coluna "produto_id" ir referenciar dentro da nova tabela o id do Produto(referencia a entidade que está)
    inverseJoinColumns = @JoinColumn(name = "categoria_id")) //  cria a colina categoria_id referenciando a categoria.(referencia o atributo)
    private List<Categoria> categorias;

    @OneToOne(mappedBy = "produto")
    private Estoque estoque;

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;
    // updatable = false -> Esse atributo nunca será alterado quando ouve algum update.
    // obviamente por ser uma data de criação a intenção é que seja um data unica na hora da sua inserção


    @Column(name = "data_ultima_atualizacao", insertable = false)
    private LocalDateTime dataUltimaAtualizacao;
    // insertable = false- > Esse atributo nunca será setado dentro da criação desse objeto, apenas nas atualizações

}
