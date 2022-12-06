package com.trip.advisory.controller;

import com.trip.advisory.dto.InfoDTO;
import com.trip.advisory.dto.SearchCriteriaDTO;
import com.trip.advisory.service.AdvisoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdvisoryController {

    @Autowired
    private AdvisoryService advisoryService;

    @GetMapping("/advise")
    public InfoDTO advise(@Validated SearchCriteriaDTO searchCriteriaDto) {
        logger.log(searchCriteriaDto.getCity());


        return advisoryService.getAdvise(searchCriteriaDto);
    }

}
