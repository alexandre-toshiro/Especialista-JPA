package com.algaworks.ecommerce.CRelacionamentos;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BRelacionamentoOneToManyTest extends EntityManagerTest {

    //Nessa caso mapeamos a volta na classe Cliente com o mappedBy
    // deve-se avaliar a necessidade de mapeamento da volta

    @Test
    public void verificarRelacionamento() {
        Cliente cliente = entityManager.find(Cliente.class, 1);
        Pedido pedido = new Pedido();
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setCliente(cliente);

        // Com o mapeamento da volta, poderiamos criar uma lista de pedidos e colocar dentro do cliente.
        // Sempre temos que avaliar a necessidade de tal complexidade, no caso n é necessário
        // Mas se tivessemos alguma regra mais complexa, que deveria ser feita em cada pedido, por exemplo
        // seria melhor fazer ao contrário, tudo depende de análise e real necessidade.

        entityManager.getTransaction().begin();
        entityManager.persist(pedido);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
        Assertions.assertFalse(clienteVerificacao.getPedidos().isEmpty());
        // deve ser false, pois deve ter pelo menos 1 pedido, que inserimos acima.
    }

    @Test
    public void verificarRelacionamentoExercicio() {
      // foi mapeado na classe Pedido, uma lista de ItemPedido

        Pedido pedido = new Pedido();
        pedido.setTotal(new BigDecimal(12));
        pedido.setDataConclusao(LocalDateTime.now());
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setQuantidade(3);
        itemPedido.setPedido(pedido);

        entityManager.getTransaction().begin();
        entityManager.persist(pedido);
        entityManager.persist(itemPedido);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        Assertions.assertFalse(pedidoVerificacao.getItens().isEmpty());
        // Pela correção eu ´pderia ter pego do banco com find, um cliente e um produto
        // cliente eu colocaria dentro do pedido e o produto dentro do itempedido
    }
}
