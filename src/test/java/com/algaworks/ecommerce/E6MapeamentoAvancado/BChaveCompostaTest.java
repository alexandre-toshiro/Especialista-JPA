package com.algaworks.ecommerce.E6MapeamentoAvancado;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class BChaveCompostaTest extends EntityManagerTest {

    //1 - ) Acrescentar dois atributos que irão compor a chave composta na classe ItemPedido
    //2) - Será criada a classe ItemPedidoId, e esses mesmos dois atributos serão inseridos nela. Mas sem anotação do JPA
    //3) - Voltando a classe item pedido iremos anotas a classe com "@IdClass(ItemPedidoId.class)" indicando qual classe será o "id" dela.
    //4) - Na classe ItemPedido, nos atributos de ENTIDADE Pedido e Produto, deve ser adicionado "insertable = false, updatable = false"
    // Pois assim sinalizamos que a responsabilidade de inserir os ids não é daquele atributo em si, mas sim dos especificos que estão acima
    // Mantemos os dois, pois os atributos do tipo das Entidades fazem o relacionamento, enquanto os ids, fazem a chave composta.

    @Test
    public void salvarItem() {
        entityManager.getTransaction().begin();

        Cliente cliente = entityManager.find(Cliente.class, 1);
        Produto produto = entityManager.find(Produto.class, 1);

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setTotal(produto.getPreco());

        entityManager.persist(pedido);

        entityManager.flush();

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setPedidoId(pedido.getId());
        itemPedido.setProdutoId(produto.getId());
        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);
        itemPedido.setPrecoProduto(produto.getPreco());
        itemPedido.setQuantidade(1);

        entityManager.persist(itemPedido);

        entityManager.getTransaction().commit();

        entityManager.clear();

        Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        Assertions.assertNotNull(pedidoVerificacao);
        Assertions.assertFalse(pedidoVerificacao.getItens().isEmpty());
    }

    @Test
    public void bucarItem() {
        ItemPedido itemPedido = entityManager.find(
                ItemPedido.class, new ItemPedidoId(1, 1));

        Assertions.assertNotNull(itemPedido);
    }


}
