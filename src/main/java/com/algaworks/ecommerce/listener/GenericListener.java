package com.algaworks.ecommerce.listener;

import javax.persistence.PostLoad;

public class GenericListener {

    @PostLoad
    public void logCarregamento(final Object object) {
        System.out.println("entidade carregada " + object.getClass().getSimpleName());
    }
}
