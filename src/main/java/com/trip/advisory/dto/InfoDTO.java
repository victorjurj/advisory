package com.trip.advisory.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Value
@Builder
public class InfoDTO {

    private Long id;
    private String city;
    private Date startDate;
    private Date endDate;
    private String hotel;
    private Map<String, BigDecimal> temperatureByDay;

}
