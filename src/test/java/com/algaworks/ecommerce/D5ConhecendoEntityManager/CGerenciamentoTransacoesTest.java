package com.algaworks.ecommerce.D5ConhecendoEntityManager;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.StatusPedido;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CGerenciamentoTransacoesTest extends EntityManagerTest {

    @Test
    public void abrirFecharCacelarTransacao() {
        try {
            entityManager.getTransaction().begin();
            Assertions.assertThrows(Exception.class, () -> metodoDenegocio());


            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }

    }

    private void metodoDenegocio() {
        Pedido pedido = entityManager.find(Pedido.class, 1);
        pedido.setStatus(StatusPedido.PAGO);

        if (pedido.getPagamento() == null) {
            throw new RuntimeException("Pedido ainda não foi pago");
        }
    }
}
//entityManager.getTransaction().rollback(); dificilmente usado, pois quando se manda um dado pra uma transação, geralmente ja foi feita todas as valid necessárias
