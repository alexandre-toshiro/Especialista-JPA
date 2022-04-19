package com.algaworks.ecommerce.CRelacionamentos;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DRemovendoEntidadesReferenciadasTest extends EntityManagerTest {

    @Test
    public void removerEntidadeRelacionada() {
        Pedido pedido = entityManager.find(Pedido.class, 1);

        Assertions.assertFalse(pedido.getItens().isEmpty());

        entityManager.getTransaction().begin();

        // Como um pedido está associado a um item pedido, o banco não deixa excluir, pois ele possui uma associação
        // Um pedido possui itemspedidos dentro dele, então não podemos exclui-lo
        // Para isso iremos varrer a lista de item pedido e excluir todos que estejam ali.


        pedido.getItens().forEach(item -> entityManager.remove(item));
        entityManager.remove(pedido);
        entityManager.getTransaction().commit();

        Pedido pedidoVerificacao = entityManager.find(Pedido.class, 1);
        Assertions.assertNull(pedidoVerificacao);

    }
}
