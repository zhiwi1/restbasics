package com.epam.esm.service.dto;

import com.epam.esm.entity.OrderType;
import com.epam.esm.entity.SortType;
import com.epam.esm.service.exception.ExceptionMessageKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateQueryParamDto {
    @Size(max=100,message = ExceptionMessageKey.NOT_VALID_SIZE)
    private String tagName;
    @Size(max=100,message = ExceptionMessageKey.NOT_VALID_SIZE)
    private String name;
    @Size(max=200,message = ExceptionMessageKey.NOT_VALID_SIZE)
    private String description;
    private SortType sortType;
    private OrderType orderType;
}
