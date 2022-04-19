package com.algaworks.ecommerce.CRelacionamentos;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ERelacionamentoManyToManyTest extends EntityManagerTest {

    @Test
    public void verificarRelacionamento() {
        Produto produto = entityManager.find(Produto.class, 1);
        Categoria categoria = entityManager.find(Categoria.class, 1);

        entityManager.getTransaction().begin();

       // categoria.setProdutos(Arrays.asList(produto)); isso não irá funcionar, pois o atributo produto dentro de categoria não é o owner
        produto.setCategorias(Arrays.asList(categoria));

        //Não foi utilizado o merge nem o persist, foi ambas a entidades jpa estão sendo sendo gerenciadas pelo
        // entityManager, foram acima buscadas por ele no banco
        entityManager.getTransaction().commit();

        entityManager.clear();

        Categoria categoriaVerificacao = entityManager.find(Categoria.class, categoria.getId());
        Assertions.assertFalse(categoriaVerificacao.getProdutos().isEmpty());
        // A asserção continua a mesma mesmo o getProdutos não sendo owner, pois dessa forma podemos verificar o relacionamento
        // Mas ele não pode ser modificado atraves do atributo produto dentro de categoria.


    }
}
