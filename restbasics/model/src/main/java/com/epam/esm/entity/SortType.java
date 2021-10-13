package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum SortType {
    ASC(" ASC"),
    DESC(" DESC");

    private final String sqlExpression;

    @JsonCreator
    public static SortType decode(final String code) {
        return Stream.of(SortType.values())
                .filter(targetEnum -> targetEnum.sqlExpression.trim().equalsIgnoreCase(code))
                .findFirst()
                .orElse(ASC);
    }
}
