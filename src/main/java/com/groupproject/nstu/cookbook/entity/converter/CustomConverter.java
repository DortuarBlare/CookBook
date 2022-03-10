package com.groupproject.nstu.cookbook.entity.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupproject.nstu.cookbook.entity.Ingredient;

import javax.persistence.AttributeConverter;
import java.util.List;

public class CustomConverter implements AttributeConverter<List<Ingredient>, String> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Ingredient> object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public List<Ingredient> convertToEntityAttribute(String data) {
        try {
            return objectMapper.readValue(data, new TypeReference<List<Ingredient>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}