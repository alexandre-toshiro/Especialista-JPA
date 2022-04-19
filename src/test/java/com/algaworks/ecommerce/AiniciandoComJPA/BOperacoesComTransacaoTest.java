package com.algaworks.ecommerce.AiniciandoComJPA;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class BOperacoesComTransacaoTest extends EntityManagerTest {

    @Test
    public void abrirEFecharATransacao() {//1
        // Produto produto = new Produto();// somente para não dar erro

        entityManager.getTransaction().begin();
        // Para realizar operações(no banco) que faça algum alteração precisamos estar dentro de uma transação

        //entityManager.persist(produto);
        //entityManager.merge(produto);
        //entityManager.remove(produto);

        entityManager.getTransaction().commit();

    }

    @Test
    public void inserirPrimeiroObjeto() {//2
        Produto produto = new Produto();
        //produto.setId(2);
        produto.setNome("Camera Canon");
        produto.setDescricao("A melhor definição para suas fotos");
        produto.setPreco(new BigDecimal(5000));

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();
      /*  2 - O código de ação(persist, merge, remove) não precisam estar necessariamente entre
            o begin e o commit de um transação, ele fica sendo gerenciado pelo entityManager
            e quando ele acha uma transação aberta ele realiza a operação de fato. */

        entityManager.clear();
       /* 1 - Um entidade quando é persistida, ela fica na memória do entityManager
            então quando fazemos abaixo um "find" ele não busca no banco, pois tal entidade
            ainda está sendo gerenciada pelo entityManager.
            Então utilizamos o método "clear" para limpar o entity manager e o "find" va até o banco
            dando uma maior "segurança" de que de fato aquele produto está correto no banco. */

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assertions.assertNotNull(produtoVerificacao);

      /*  3 - Com o método entityManager.flush(); forçamos o que está na memória do entityManager
                a ir para o banco de dados. Inclusive antes de dar o commit do transaction
                é executado um "flush".
        (o exemplo mostrado, foi quando não se tem uma transação em andamento, ai foi utilizado o flush) */

    }

    @Test
    public void removerObjeto() {
        Produto produto = entityManager.find(Produto.class, 3);

       /* Para podermos excluir uma entidade do banco de dados, ela estar sendo gerenciada pelo entityManager
                então não podemos por exemplo dar new no Produto com id 3 e depois remove-lo.
                Devemos buscar no banco com o find, o entityManager o gerencia e após isso fazemos a exclusão. */

        entityManager.getTransaction().begin();
        entityManager.remove(produto);
        entityManager.getTransaction().commit();

        // entityManager.clear(); n é necessário para operação de remoção, já que como ele foi removido do banco
        // será removido da memória do entityManager também.

        Produto produtoVerificacao = entityManager.find(Produto.class, 3);
        Assertions.assertNull(produtoVerificacao);
    }

    @Test
    public void atualizarObjeto() {
        Produto produto = new Produto();
        produto.setId(1);
        produto.setNome("Kindle Paperwhite");
        produto.setDescricao("Conheça o novo Kindle");
        produto.setPreco(new BigDecimal(599));

        /* 1 - Nessa caso em especifico como estamos montando o objeto e não dando um find nele pelo banco
                essa entidade não está sendo gerenciada pelo entityManager, então ele não o conhece.
                Devemos preencher todos os atributos, pois quando cair na atualização, o entityManager
                entende que caso algum campo não esteja preenchido, ele NÃO irá ignorar, ele irá setar como nullo
                Então nesse caso temos que preencher tudo, a não ser que de fato quisermos algum atributo nullo. */


        entityManager.getTransaction().begin();
        entityManager.merge(produto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assertions.assertNotNull(produtoVerificacao);
        Assertions.assertEquals("Kindle Paperwhite", produtoVerificacao.getNome());

    }

    @Test
    public void atualizarObjetoGerenciado() {
        Produto produto = entityManager.find(Produto.class, 1);


        entityManager.getTransaction().begin();
        produto.setNome("Kindle Paperwhite 2º geração"); // também pode ser feita antes da transação
        // Como já está gerenciado pelo entityManager, não precisamos do merge, apenas alteração dos atributos.
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assertions.assertNotNull(produtoVerificacao);
        Assertions.assertEquals("Kindle Paperwhite 2º geração", produtoVerificacao.getNome());

    }

    @Test
    public void inserirObjetoComMerge() {
        Produto produto = new Produto();
        //produto.setId(4);
        produto.setNome("Microfone Rode Videmic");
        produto.setDescricao("A melhor qualidade de som");
        produto.setPreco(new BigDecimal(1000));

        entityManager.getTransaction().begin();
        Produto produtoSalvo = entityManager.merge(produto);
        // também é possível inserir um dado no banco com método "merge".
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produtoSalvo.getId());
        Assertions.assertNotNull(produtoVerificacao);


    }

    @Test
    public void impedirOperacaoComBancoDeDados() {
        Produto produto = entityManager.find(Produto.class, 1);
        entityManager.detach(produto); // Desanexa a entidade do controle do entityManager
        // Desanexa uma instância que está na memória do entityManager.

        entityManager.getTransaction().begin();
        produto.setNome("Kindle Paperwhite 2º geração");
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assertions.assertNotNull(produtoVerificacao);
        Assertions.assertEquals("Kindle", produtoVerificacao.getNome());

    }
}
