package com.epam.esm.service.util;

import com.epam.esm.service.dto.GiftCertificateQueryParamDto;
import lombok.experimental.UtilityClass;

import java.text.MessageFormat;

@UtilityClass
public class GiftCertificateQueryCreator {
    private static final String TAG_NAME = "tags.name LIKE '%s'";
    private static final String WHERE = " WHERE ";
    private static final String AND = " AND ";
    private static final String NAME = "certificates.name LIKE ''%{0}%''";
    private static final String DESCRIPTION = "description LIKE ''%{0}%''";
    private static final String GROUP_BY = " GROUP BY certificates.id";

    public static String createQuery(GiftCertificateQueryParamDto giftCertificateQueryParameters) {
        StringBuilder condition = new StringBuilder();
        addTagName(giftCertificateQueryParameters, condition);
        addName(giftCertificateQueryParameters, condition);
        addDescription(giftCertificateQueryParameters, condition);
        condition.append(GROUP_BY);
        addSortType(giftCertificateQueryParameters, condition);
        return condition.toString();
    }

    private static void addTagName(GiftCertificateQueryParamDto giftCertificateQueryParameters, StringBuilder condition) {
        if (giftCertificateQueryParameters.getTagName() != null) {
            addOperator(condition);
            condition.append(String.format(TAG_NAME, giftCertificateQueryParameters.getTagName()));
        }
    }


    private static void addName(GiftCertificateQueryParamDto giftCertificateQueryParameters, StringBuilder condition) {
        if (giftCertificateQueryParameters.getName() != null) {
            addOperator(condition);
            condition.append(MessageFormat.format(NAME, giftCertificateQueryParameters.getName()));
        }
    }

    private void addDescription(GiftCertificateQueryParamDto giftCertificateQueryParameters,
                                StringBuilder condition) {
        if (giftCertificateQueryParameters.getDescription() != null) {
            addOperator(condition);
            condition.append(MessageFormat.format(DESCRIPTION, giftCertificateQueryParameters.getDescription()));
        }
    }

    private static void addSortType(GiftCertificateQueryParamDto giftCertificateQueryParameters, StringBuilder condition) {
        if (giftCertificateQueryParameters.getSortType() != null) {
            condition.append(giftCertificateQueryParameters.getSortType().getSqlExpression());
            if (giftCertificateQueryParameters.getOrderType() != null) {
                condition.append(giftCertificateQueryParameters.getOrderType().getSqlExpression());
            }
        }
    }

    private static void addOperator(StringBuilder condition) {
        condition.append((condition.length() == 0) ? WHERE : AND);
    }
}