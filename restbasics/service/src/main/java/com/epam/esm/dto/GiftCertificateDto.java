package com.epam.esm.dto;

import com.epam.esm.exception.ExceptionMessageKey;
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
    @Range(min = 0)
    private long id;
    @Size(max=100)
    @Pattern(regexp = "[\\D ]+")
    private String name;
    @Pattern(regexp = "[\\D ]+")
    @Size(max=200)
    private String description;
    @Range(min = 0)
    private BigDecimal price;
    @Range(min = 0, max = Integer.MAX_VALUE)
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

