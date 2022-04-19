package com.algaworks.ecommerce.D5ConhecendoEntityManager;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.StatusPedido;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FCallbackTest extends EntityManagerTest {

    @Test
    public void acionarCallbacks() {
        // Foi adicionado na classe pedido os métodos de callBack prePersis e preUpdate
        // Que fazem justamente atualização antes de salvar e antes de atualizar
        // o prePersist é apenas executado uma vez por entidade, e o preUpdate é executado toda vez que ela atualiza.
        // Um call back são "métodos" que fazemos para alterar atributos que estão fortemente ligados aquela entidade


        Cliente cliente = entityManager.find(Cliente.class, 1);

        Pedido pedido = new Pedido();

        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.AGUARDANDO);

        entityManager.getTransaction().begin();

        entityManager.persist(pedido);// aqui o @Prepersist é acionado
        entityManager.flush();

        pedido.setStatus(StatusPedido.PAGO);
        entityManager.getTransaction().commit(); // aqui será acionado o @Preupdate

        entityManager.clear();

        Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());

        Assertions.assertNotNull(pedidoVerificacao.getDataCriacao());
        Assertions.assertNotNull(pedidoVerificacao.getDataUltimaAtualizacao());
    }
}
