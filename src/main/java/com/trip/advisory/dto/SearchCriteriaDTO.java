package com.trip.advisory.dto;

import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Value
public class SearchCriteriaDTO {

    @NotNull
    private String city;
    @NotNull
    private String lat;
    @NotNull
    private String lon;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date startDate;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date endDate;
    @NotNull
    private String hotel;

}
