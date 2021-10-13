package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateInputDto {
    @Size(max = 100)
    @Pattern(regexp = "[\\D ]+")
    private String name;
    @Pattern(regexp = "[\\D ]+")
    @Size(max = 200)
    private String description;
    @Range(min = 0)
    private BigDecimal price;
    @Range(min = 0, max = Integer.MAX_VALUE)
    private int duration;
}
