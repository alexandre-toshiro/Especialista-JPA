package com.algaworks.ecommerce.BMapeamentoBasico;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.SexoCliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AMapeandoEnumeracoesTest extends EntityManagerTest {

    @Test
    public void testarEnum() {
        Cliente cliente = new Cliente();
        cliente.setNome("José Mineiro");
        cliente.setSexo(SexoCliente.MASCULINO);

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
        Assertions.assertNotNull(clienteVerificacao);

        /* O perigo de se usar ENUM da forma padrão, é que no banco é salvo a posição numeral do enum, no caso masculino = 0
        e feminino = 1(como se fosse array). Então por estarmos salvando como masculino, irá salvar no banco 0. Mas se futuramente
        os enums forem trocado de posições por qualquer motivo, então no banco aquele dado irá continuar como 0, mas caso a troca seja feita
        o feminino será o 0.
        PARA RESOLVER, na classe cliente anotamos o ENUM com   @Enumerated(EnumType.STRING), que irá salvar no banco como string
        o nome do enum.
             */
    }
}
