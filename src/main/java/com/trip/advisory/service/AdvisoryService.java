package com.trip.advisory.service;

import com.trip.advisory.dto.InfoDTO;
import com.trip.advisory.dto.SearchCriteriaDTO;

public interface AdvisoryService {

    InfoDTO getAdvise(SearchCriteriaDTO searchCriteriaDTO);

}
