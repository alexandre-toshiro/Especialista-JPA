package com.algaworks.ecommerce.D5ConhecendoEntityManager;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.StatusPedido;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GListenerTest extends EntityManagerTest {

    @Test
    public void acionandoListener() {
        // foi criado a classe GerarNostaFiscalListener e NotaFiscalService
        // na classe pedido foi feita um método chamado "isPago"
        // Na classe pedido, para que o listener possa ser executado temos que colocar antes do nome da classe a anotação
        // @EntityListeners({GerarNotaFiscalListener.class}), que recebe um array de listeners, no casso temos apenas um.

        // Um listener é faz uma "integração" outras entidades que podem executar algo por tabela
        // Por exemplo, a partir de um pedido PAGO é gerada uma nota fiscal.

        Cliente cliente = entityManager.find(Cliente.class, 1);

        Pedido pedido = new Pedido();

        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.AGUARDANDO);

        entityManager.getTransaction().begin();

        entityManager.persist(pedido);
        entityManager.flush();

        pedido.setStatus(StatusPedido.PAGO);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());

        Assertions.assertNotNull(pedidoVerificacao.getDataCriacao());
        Assertions.assertNotNull(pedidoVerificacao.getDataUltimaAtualizacao());
    }

    @Test
    public void carregarEntidades() {
        Pedido pedido = entityManager.find(Pedido.class, 1);
        Produto produto = entityManager.find(Produto.class, 1);

        // olhar os logs para ver os prints dos liteners
    }
}
