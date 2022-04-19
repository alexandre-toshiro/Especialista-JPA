package com.algaworks.ecommerce.E6MapeamentoAvancado;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ADetalhesColumnTest extends EntityManagerTest {

    // na classe produto foi inserida dois novos atributos data de criação e ultima atualização
    // e foram anotados com @Column, com argumentos updatable e insertable como false

    @Test
    public void impedirInsercaoDaColunaAtualizacao() {
        Produto produto = new Produto();
        produto.setNome("Teclado para smartphone");
        produto.setDescricao("O mais confortável");
        produto.setPreco(BigDecimal.ONE);
        produto.setDataCriacao(LocalDateTime.now());
        produto.setDataUltimaAtualizacao(LocalDateTime.now());

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assertions.assertNotNull(produtoVerificacao.getDataCriacao()); // como é a criação de um produto, essa data precisa estar preenchida
        Assertions.assertNull(produtoVerificacao.getDataUltimaAtualizacao());// como é um data que só pode ser peenchidade numa atualização, deve estar nula
    }

    @Test
    public void impedirAtualizacaoDaColunaCriacao() {
        Produto produto = entityManager.find(Produto.class, 1);
        produto.setPreco(BigDecimal.TEN);
        produto.setDataCriacao(LocalDateTime.now());// não pode ser inserida
        produto.setDataUltimaAtualizacao(LocalDateTime.now());

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());

        Assertions.assertNotEquals(produto.getDataCriacao().truncatedTo(ChronoUnit.SECONDS),
                produtoVerificacao.getDataCriacao().truncatedTo(ChronoUnit.SECONDS)); // Não pode ser igual, pois não pode ter sido atualizado
        // aqui o truncated não faz mta diferença, pois de qualquer forma os dois devem ser diferentes

        Assertions.assertEquals(produto.getDataUltimaAtualizacao().truncatedTo(ChronoUnit.SECONDS),
                produtoVerificacao.getDataUltimaAtualizacao().truncatedTo(ChronoUnit.SECONDS));
        // aqui o truncated faz diferença, pois os dois devem ser iguais, e a diferença  de precisão do JAVA e do BD, pode interferir
        // na comparação(precisão dos nanos segundos podem diferir)

        //truncatedTo - A precisão do JAVA é maior que a do BD, então truncamos para segundos de forma a não ter problemas com as comparações

    }





































}
