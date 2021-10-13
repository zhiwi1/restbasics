package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum OrderType {
    NAME("name", " ORDER BY name"),
    CREATE_DATE("date", " ORDER BY create_date");

    private final String name;
    private final String sqlExpression;

    @JsonCreator
    public static OrderType decode(final String code) {
        return Arrays.stream(values())
                .filter(orderType -> orderType.name.equalsIgnoreCase(code))
                .findFirst()
                .orElse(null);
    }
}