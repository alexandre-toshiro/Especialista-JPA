package com.algaworks.ecommerce.CRelacionamentos;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GEagerELazyTest extends EntityManagerTest {

    @Test
    public void verificarComportamento() {
        // na classe Pedido no atributo itemPedidos a lista foi anotada com Fetch = eager
        // Para que ao carregar o pedido, já traga as listas juntos
        Pedido pedido = entityManager.find(Pedido.class, 1);

        boolean empty = pedido.getItens().isEmpty();

        Assertions.assertFalse(empty);
    }
}
