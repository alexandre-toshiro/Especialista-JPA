package com.algaworks.ecommerce.AiniciandoComJPA;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AConsultandoRegistrosTest extends EntityManagerTest {


    @Test
    public void buscarPorIdentificador() {
        Produto produto = entityManager.find(Produto.class, 1);
        //Produto produto = entityManager.getReference(Produto.class, 1);
        // apenas busca quando é utilizada alguma propriedade da entidade

        //System.out.println("AINDA NÃO BUSCOU.");

        assertNotNull(produto);
        assertEquals("Kindle", produto.getNome()); // a entidade é buscado no banco aqui(getReference)
    }

    @Test
    public void atualizaReferencia() {
        Produto produto = entityManager.find(Produto.class, 1);
        produto.setNome("Microfone Samson");

        entityManager.refresh(produto);
        // Volta ao estado que está no banco.
        // Será feito dois selects um do FIND e outro do refresh
        // o setNome, não altera nada no banco, apenas no "código".

        assertEquals("Kindle", produto.getNome());
    }
}
