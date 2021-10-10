package com.epam.esm.service.dto;

import com.epam.esm.service.exception.ExceptionMessageKey;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateDto {
    @Range(min = 0,message = ExceptionMessageKey.VALUE_NOT_IN_RANGE)
    private long id;
    @Size(max=100, message = ExceptionMessageKey.NOT_VALID_SIZE)
    @Pattern(regexp = "[\\D ]+",message = ExceptionMessageKey.INVALID_INPUT)
    private String name;
    @Pattern(regexp = "[\\D ]+",message = ExceptionMessageKey.INVALID_INPUT)
    @Size(max=200,message = ExceptionMessageKey.NOT_VALID_SIZE)
    private String description;
    @Range(min = 0,message = ExceptionMessageKey.VALUE_NOT_IN_RANGE)
    private BigDecimal price;
    @Range(min = 0, max = Integer.MAX_VALUE,message = ExceptionMessageKey.VALUE_NOT_IN_RANGE)
    private int duration;
    private Set<TagDto> tags;
    @JsonProperty("create_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private ZonedDateTime createDate;
    @JsonProperty("last_update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private ZonedDateTime lastUpdateDate;

}

