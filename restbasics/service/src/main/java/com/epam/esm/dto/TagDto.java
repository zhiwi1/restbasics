package com.epam.esm.dto;


import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {
    @Range(min = 0)
    private long id;
    @Size(min=2,max=100)
    @Pattern(regexp = "[\\D ]+")
    private String name;
}