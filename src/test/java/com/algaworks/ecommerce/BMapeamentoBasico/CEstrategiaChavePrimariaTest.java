package com.algaworks.ecommerce.BMapeamentoBasico;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.Estoque;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CEstrategiaChavePrimariaTest extends EntityManagerTest {

    @Test
    public void testarEstartegia() {
        Categoria categoria = new Categoria();
        categoria.setNome("Dorgas");

        entityManager.getTransaction().begin();
        entityManager.persist(categoria);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Categoria categoriaVerificacao = entityManager.find(Categoria.class, categoria.getId());
        Assertions.assertNotNull(categoriaVerificacao);
    }
}
