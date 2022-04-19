package com.algaworks.ecommerce.AiniciandoComJPA;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class CDiferencaMerge_Persist extends EntityManagerTest {

    @Test
    public void explicandoPersist() {
        Produto produto = new Produto();
        //produto.setId(5);
        produto.setNome("Smartphone One Plus");
        produto.setDescricao("O Processador mais rápido");
        produto.setPreco(new BigDecimal(2000));

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
       /* O método persist serve apenas para persistir um dado, ou seja ele somente pra fazer o insert no banco
                Se faz necessário quando vc quer que uma chamada faça com garantia uma inserção de um NOVO registro
                NO persist ele coloca a MESMA instância dentro do entityManager para ser gerenciada. */
        produto.setNome("Smartphone Two Plus");
        // Como é a mesma instância, se fizermos alguma alteração aqui, ele faz um update após a inserção.
        // Pode ser visto um "update" no log
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assertions.assertNotNull(produtoVerificacao);
    }

    @Test
    public void explicandoMerge() {
        Produto produto = new Produto();
        produto.setId(6);
        produto.setNome("NoteBook Dell");
        produto.setDescricao("O Melhor da categoria");
        produto.setPreco(new BigDecimal(2000));

        entityManager.getTransaction().begin();


        produto = entityManager.merge(produto);
        // 1) - Pode ser utilizado pra atualizar e salvar
        // 2) - Não pega a mesma instância e passa para o entityManager, mas sim faz uma cópia.
        // 3) - Como é feita uma cópia, podemos ou atribuir a uma nova variavel, ou "sobrescrever" a já existente.

        produto.setNome("Notebook Dell 2");
        // Como essa instância não está sendo gerenciada, já que o Merge faz uma cópia dela e essa cópia que está sendo gerenciada
        // ao fazermos esse "setNome" não acontece nada.
        // para que esse setNome funcione, devemos pegar a copia que o merge devolve, atribuindo ela a variavel produto
        // e agora sim o setNome passa a funcionar



        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assertions.assertNotNull(produtoVerificacao);
    }

}
