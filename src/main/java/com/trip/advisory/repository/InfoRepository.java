package com.trip.advisory.repository;

import com.trip.advisory.InfoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface InfoRepository extends CrudRepository<InfoEntity, Long> {

    InfoEntity findByCityAndLatAndLonAndStartDateAndEndDateAndHotel(String city, String lat, String lon, Date startDate, Date endDate, String hotel);

}
