package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonCreator;


public enum SortType {

    NAME(" ORDER BY name"),
    CREATE_DATE(" ORDER BY create_date");

    private final String sqlExpression;

    SortType(String sqlExpression) {
        this.sqlExpression = sqlExpression;
    }

    @JsonCreator
    public static SortType decode(final String code) {
        if (code.equalsIgnoreCase("name")) {
            return SortType.NAME;
        } else if (code.equalsIgnoreCase("date")) {
            return SortType.CREATE_DATE;
        }
        return null;
    }

    public String getSqlExpression() {
        return sqlExpression;
    }
}