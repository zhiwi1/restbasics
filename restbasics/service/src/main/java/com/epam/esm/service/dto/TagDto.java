package com.epam.esm.service.dto;

import com.epam.esm.service.exception.ExceptionMessageKey;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {
    @Range(min = 0,message = ExceptionMessageKey.VALUE_NOT_IN_RANGE)
    private long id;
    @Size(min=2,max=100, message = ExceptionMessageKey.NOT_VALID_SIZE)
    @Pattern(regexp = "[\\D ]+")
    private String name;
}