package com.algaworks.ecommerce.D5ConhecendoEntityManager;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.StatusPedido;
import org.junit.jupiter.api.Test;

public class DFlushTest extends EntityManagerTest {

    @Test
    public void chamarFlush() {

        try {
            entityManager.getTransaction().begin();

            Pedido pedido = entityManager.find(Pedido.class, 1);
            pedido.setStatus(StatusPedido.PAGO);
            System.out.println("-------------------EXECUTANDO O FLUSH AQUI-------------------");
            entityManager.flush();//flush pega o que esta na memória neste momento e joga pro banco de dados
            // abaixo ele daria erro, cairia no catch e seria dado um rollback não salvando essa alteração
            // mas com o flush conseguimos contornar isso.
            // de forma geral não é muito recomendado utilizar o flush para não "atropelar" as transações
            System.out.println("-------------------TERMINANDO O FLUSH AQUI-------------------");


            if (pedido.getPagamento() == null) {
                throw new RuntimeException("Pedido ainda não foi pago");
            }

            // Uma consulta com JPQL, obriga o JPA  sincronizar o que ele tem na memória.
            // Digamos que tivesse a alteração acima, mas sem o flush naquele ponto.
            // Aqui fazemos uma JPQL, então nesse ponto, seria dado um update e dps seria feito a consulta da JPQL

            entityManager.getTransaction().commit();
        } catch (Exception e) { entityManager.getTransaction().rollback();
            throw e;
        }
    }
}
