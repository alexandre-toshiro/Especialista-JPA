package com.algaworks.ecommerce.D5ConhecendoEntityManager;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Categoria;
import org.junit.jupiter.api.Test;

public class AEstadosECicloDeVidaTest extends EntityManagerTest {

    @Test
    public void analisarEstados() {

        Categoria categoria = new Categoria();// estado novo(Transient)

        Categoria categoriaGerenciadaMerge = entityManager.merge(categoria);//merge devolve uma copia gerenciada


        Categoria categoriaGerenciada = entityManager.find(Categoria.class, 1);// estado managed/gerenciado

        entityManager.remove(categoriaGerenciada); //estado removed

        entityManager.persist(categoriaGerenciada);//volta a ser managed

        

    }
}
