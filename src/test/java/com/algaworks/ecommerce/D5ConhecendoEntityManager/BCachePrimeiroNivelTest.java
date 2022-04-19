package com.algaworks.ecommerce.D5ConhecendoEntityManager;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.Test;

public class BCachePrimeiroNivelTest extends EntityManagerTest {

    @Test
    public void verificarCache() {
        Produto produto = entityManager.find(Produto.class, 1);

        System.out.println(produto.getNome());
        System.out.println("-----------------------");

        Produto produtoResgatado = entityManager.find(Produto.class, 1);

        System.out.println(produtoResgatado.getNome());

        /*
        Buscar o produto de id 1, vai colocar o objeto na memória
        Quando ele for buscar de novo o mesmo id, ele vai entender que será buscado o mesmo identificador
        Será feita uma busca na memória dele, se já existe esse objeto lá, no exemplo acima já existe.
        No console, será mostrada apenas uma busca e em baixo do traçado, será mostrado apenas o nome.
        Isso é o cache de primeiro de nível do entityManager.
        Por obvio, esse cache economiza consultas, caso seja feita uma busca do mesmo objeto várias vezes
        não precisará ir no banco por diversas vezes.
         */


    }
}
