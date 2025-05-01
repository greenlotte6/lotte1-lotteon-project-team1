package com.example.lotteon.converter;

import jakarta.persistence.AttributeConverter;

public class BooleanToEnumStringConverter implements AttributeConverter<Boolean, String> {

  @Override
  public String convertToDatabaseColumn(Boolean attribute) {
    return Boolean.TRUE.equals(attribute) ? "1" : "0";
  }

  @Override
  public Boolean convertToEntityAttribute(String dbData) {
    return "1".equals(dbData);
  }
}
