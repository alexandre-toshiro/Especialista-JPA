package com.algaworks.ecommerce.D5ConhecendoEntityManager;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class EContextoDePersistenciaTest extends EntityManagerTest {

    @Test
    public void usarContextoPersistencia() {
        entityManager.getTransaction().begin();
        Produto produto = entityManager.find(Produto.class, 1); // esse produto está dentro desse contexto de persistencia
        produto.setPreco(new BigDecimal(100));// dirty checking
        /*
        Dirty checking - é quando uma instância JÁ GERENCIADA(já salva) sofre alguma alteração, como é no caso
        então ele checa a "sujeira" e manda um update pro banco com a alteração acima.
         */

        Produto produto2 = new Produto();
        produto2.setNome("Caneca para café");
        produto2.setPreco(new BigDecimal(10));
        produto2.setDescricao("Café muito bom");

        entityManager.persist(produto2); // contexto de persistência

        Produto produto3 = new Produto();
        produto3.setNome("Dorgas");
        produto3.setPreco(new BigDecimal(10));
        produto3.setDescricao("dorgas para dorgas"); // aqui não é dirty checking
        produto3 = entityManager.merge(produto3);// contexto de persistência

        produto3.setDescricao("alterar descrição");// aqui é dirty checking

        /*
        Como produto3 está dentro do contexto de persistencia, apenas de fazer esse set, será executado
        um update no commit dessa transação.
         */

        entityManager.getTransaction().commit();
    }
}
