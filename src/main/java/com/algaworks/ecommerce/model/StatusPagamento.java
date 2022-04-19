package com.algaworks.ecommerce.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;


public enum StatusPagamento {

    PROCESSANDO,
    CANCELADO,
    RECEBIDO


}
