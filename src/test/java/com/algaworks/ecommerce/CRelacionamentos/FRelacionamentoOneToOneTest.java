package com.algaworks.ecommerce.CRelacionamentos;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;

public class FRelacionamentoOneToOneTest extends EntityManagerTest {

    @Test
    public void verificarRelacionamento() {

        // foi mapeado @OneToOne na classe pagamentoCartão como owner
        // E na classe Pedido como non-owning

        Pedido pedido = entityManager.find(Pedido.class, 1);

        PagamentoCartao pagamentoCartao = new PagamentoCartao();
        pagamentoCartao.setNumero("1234");
        pagamentoCartao.setStatus(StatusPagamento.PROCESSANDO);
        pagamentoCartao.setPedido(pedido);


        entityManager.getTransaction().begin();

        entityManager.persist(pagamentoCartao);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Pedido pedidoVerificacao = entityManager.find(Pedido.class, 1);
        Assertions.assertNotNull(pedidoVerificacao);
    }

    @Test
    public void verificarRelacionamentoExercicio() {
        Pedido pedido = entityManager.find(Pedido.class, 1);

        NotaFiscal notaFiscal = new NotaFiscal();
        notaFiscal.setXml("test");
        notaFiscal.setDataEmissao(new Date());
        notaFiscal.setPedido(pedido);


        entityManager.getTransaction().begin();
        entityManager.persist(notaFiscal);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Pedido pedidoVerificacao = entityManager.find(Pedido.class, 1);
        Assertions.assertNotNull(pedidoVerificacao);
    }

    /*
    Foi feita uma segunda abordagem no curso onde foi utilizado o JoinTable para @OneToOne
     @JoinTable(name = "pedido_nota_fiscal",
             joinColumns = @JoinColumn(name = "nosta_fiscal_id"),
             inverseJoinColumns = @JoinColumn(name = "pedido_id", unique = true))
      Apenas para mostrar que também é possível criar um tabela intermediárias nesse caso.
      OBS: foi colocado "unique = true" para que o pedido_id não se repita, pois como é um relacionamento 1:1
      não podemos deixar que se repita. E claro, o banco profissionalmente não deve ser gerado pelo JPA.
      Então quando estivermos codando o banco já deve estar pronto e com essas restrições.
      DE FORMA GERAL ISSO NUNCA É USADO.
     */
}
