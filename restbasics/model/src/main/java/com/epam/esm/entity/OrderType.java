package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum OrderType {
    ASC(" ASC"),
    DESC(" DESC");

    private final String sqlExpression;

    OrderType(String sqlExpression) {
        this.sqlExpression = sqlExpression;
    }
        @JsonCreator
        public static OrderType decode(final String code) {
            return Stream.of(OrderType.values()).filter(targetEnum -> targetEnum.sqlExpression.trim().equalsIgnoreCase(code)).findFirst().orElse(null);
        }

    public String getSqlExpression() {
        return sqlExpression;
    }
}
