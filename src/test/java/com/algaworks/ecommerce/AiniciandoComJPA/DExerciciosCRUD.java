package com.algaworks.ecommerce.AiniciandoComJPA;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DExerciciosCRUD extends EntityManagerTest {

    @Test
    public void buscarRegistro() {
        Cliente cliente = entityManager.find(Cliente.class, 1);

        Assertions.assertNotNull(cliente);
        Assertions.assertEquals("Fernando Medeiros", cliente.getNome());
    }

    @Test
    public void inserirRegistro() {
        Cliente cliente = new Cliente();
       // cliente.setId(3);
        cliente.setNome("Alexandre Toshiro");

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Cliente clientePersistido = entityManager.find(Cliente.class, cliente.getId());

        Assertions.assertNotNull(clientePersistido);
        Assertions.assertEquals("Alexandre Toshiro", clientePersistido.getNome());
    }

    @Test
    public void atualizarRegistroFIND() {
        Cliente cliente = entityManager.find(Cliente.class, 1);
        cliente.setNome("Dorgas Lindo");

        entityManager.getTransaction().begin();
        entityManager.merge(cliente);
        entityManager.getTransaction().commit();

        entityManager.clear();
        Cliente clientePersistido = entityManager.find(Cliente.class, 1);
        Assertions.assertEquals("Dorgas Lindo", clientePersistido.getNome());
    }

    @Test
    public void atualizarRegistroCodigo() {
        Cliente cliente = new Cliente();
        //cliente.setId(1);
        cliente.setNome("Dorgas Lindo");


        entityManager.getTransaction().begin();
        entityManager.merge(cliente);
        entityManager.getTransaction().commit();

        entityManager.clear();
        Cliente clientePersistido = entityManager.find(Cliente.class, 1);
        Assertions.assertEquals("Dorgas Lindo", clientePersistido.getNome());
    }

    @Test
    public void excluirRegistro() {
        Cliente cliente = entityManager.find(Cliente.class, 2);

        entityManager.getTransaction().begin();
        entityManager.remove(cliente);
        entityManager.getTransaction().commit();

        Cliente clientePersistido = entityManager.find(Cliente.class, 2);

        Assertions.assertNull(clientePersistido);
    }
}
