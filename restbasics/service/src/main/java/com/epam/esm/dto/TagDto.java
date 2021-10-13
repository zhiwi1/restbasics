package com.epam.esm.dto;

import com.epam.esm.exception.ExceptionMessageKey;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

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