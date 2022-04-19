package com.algaworks.ecommerce.BMapeamentoBasico;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BMapeamentoObjetoEMBUTIDO extends EntityManagerTest {

    /*1) - As anotações @Embeddable e @Embedded fazem uma espécie de extensão da tabela no banco
            então mesmo que usamos classes diferentes como no exemplo abaixo, com essas anotações
            será salvo no banco NA MESMA TABELA as informações das duas classes
            Então a classe EnderecoEntregaPedido fica como uma "extensão" da classe de pedido.
    Tal separação pode ser feita para uma melhor organização do código.  */

    @Test
    public void analisarMapeamentoObjetoEmbutido() {
        EnderecoEntregaPedido endereco = new EnderecoEntregaPedido();// classe embutidade dentro do pedido
        endereco.setCep("00000-00");
        endereco.setLogradouro("Rua das Laranjeiras");
        endereco.setNumero("123");
        endereco.setBairro("Centro");
        endereco.setCidade("Uberlândia");
        endereco.setEstado("MG");

        Pedido pedido = new Pedido();
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setTotal(new BigDecimal(1000));
        pedido.setEnderecoEntrega(endereco);

        Cliente cliente = new Cliente();
        cliente.setNome("Fernando");
        cliente.setSexo(SexoCliente.MASCULINO);

        pedido.setCliente(cliente);

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);// quando cliente é novo, ele deve ser salvo primeiro
        entityManager.persist(pedido);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        Assertions.assertNotNull(pedidoVerificacao);
        Assertions.assertNotNull(pedidoVerificacao.getEnderecoEntrega());
        Assertions.assertNotNull(pedidoVerificacao.getEnderecoEntrega().getCep());
    }
}
