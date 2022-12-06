package com.trip.advisory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "advise")
@Data
@AllArgsConstructor
@Builder
public class InfoEntity {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String city;
    @Column
    private String lat;
    @Column
    private String lon;
    @Column
    private Date startDate;
    @Column
    private Date endDate;
    @Column
    private String hotel;
    @Column
    private String temperature;

}
