package com.algaworks.ecommerce.CRelacionamentos;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CAutoRelacionamentoTest extends EntityManagerTest {

    // Foi feito um auto-relacionamento dentro da classe Categoria
    // Onde a propria categoria se referencia com ManyToOne e OneToMany
    // Muitas categorias podem ter uma categoria pai e um categoria pai, pode pertencer a várias categorias "filhas"

    @Test
    public void verificarAutoRelacionamento() {
        // Criamos uma categoria pai
        Categoria categoriaPai = new Categoria();
        categoriaPai.setNome("Eletronicos");

        // Criamos uma categoria filha, um celular é um eletrônico(considerando escopo, celular poderia ser um produto)
        Categoria categoriaFilha = new Categoria();
        categoriaFilha.setNome("Celulares");
        // Setamos na filha a categoria pai
        categoriaFilha.setCategoriaPai(categoriaPai);

        entityManager.getTransaction().begin();
        entityManager.persist(categoriaPai);
        entityManager.persist(categoriaFilha);
        entityManager.getTransaction().commit();

        entityManager.clear();

        // Dentro da categoriaFilha deve ter uma categoria pai
        Categoria categoriaFilhaVerificacao = entityManager.find(Categoria.class, categoriaFilha.getId());
        Assertions.assertNotNull(categoriaFilhaVerificacao.getCategoriaPai());

       // Dentro da categoriaPai deve ter ao menos uma categoriaFilha
        Categoria categoriaPaiVerificacao = entityManager.find(Categoria.class, categoriaPai.getId());
        Assertions.assertFalse(categoriaPaiVerificacao.getCategorias().isEmpty());
    }
}
