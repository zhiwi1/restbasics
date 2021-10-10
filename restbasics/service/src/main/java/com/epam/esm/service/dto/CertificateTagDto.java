package com.epam.esm.service.dto;

import com.epam.esm.service.exception.ExceptionMessageKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateTagDto {
    @Range(min = 0,message = ExceptionMessageKey.VALUE_NOT_IN_RANGE)
    private long certificateId;
    @Range(min = 0,message = ExceptionMessageKey.VALUE_NOT_IN_RANGE)
    private long tagId;
}
