package com.algaworks.ecommerce.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToSimNaoConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean aBoolean) {
        return aBoolean? "SIM" : "NAO";
    }

    @Override
    public Boolean convertToEntityAttribute(String s) {
        return "SIM".equals(s) ? Boolean.TRUE : Boolean.FALSE;
    }
}
