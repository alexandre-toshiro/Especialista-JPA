package com.algaworks.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable // Diz pro Hibernate que essa classe é embutidade em outra
public class EnderecoEntregaPedido {

    private String cep;

    private String Logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private String cidade;

    private String estado;
}
