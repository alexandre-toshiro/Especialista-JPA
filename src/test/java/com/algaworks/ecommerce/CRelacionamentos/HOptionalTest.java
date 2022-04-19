package com.algaworks.ecommerce.CRelacionamentos;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.jupiter.api.Test;

import javax.persistence.OneToOne;
import java.math.BigDecimal;

public class HOptionalTest extends EntityManagerTest {
    //pedido - cliente,
    //estoque - produto
    // @OneToOne(optional = false)NÃO é opcional, sempre que salvar um estoque, precisa de um produto,
    // afinal não existe estoque sem produto

    /*
    Atributo optiona das anotações de relacionamento, false quando X entidade for ter que se fornecida obrigatoriamente
    como o exemplo acima de estoque e produto.
    Por padrão todos os atributos são "optiona = true"
    Com o "optional = false" o hibernate utiliza o "inner join" que é mais performático que o "left outer join"
     */

    @Test
    public void verificarComportamento() {
        Pedido pedido = entityManager.find(Pedido.class, 1);
    }


}
