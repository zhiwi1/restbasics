package com.epam.esm.dto;

import com.epam.esm.exception.ExceptionMessageKey;
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
    @Range(min = 0)
    private long certificateId;
    @Range(min = 0)
    private long tagId;
}
