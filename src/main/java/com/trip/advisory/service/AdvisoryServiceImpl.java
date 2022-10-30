package com.trip.advisory.service;

import com.trip.advisory.InfoEntity;
import com.trip.advisory.dto.InfoDTO;
import com.trip.advisory.dto.SearchCriteriaDTO;
import com.trip.advisory.repository.InfoRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AdvisoryServiceImpl implements AdvisoryService {

    @Autowired
    private InfoRepository infoRepository;

    @Override
    public InfoDTO getAdvise(SearchCriteriaDTO searchCriteriaDTO) {
//        InfoEntity infoEntity2 = InfoEntity.builder()
//                .city(searchCriteriaDTO.getCity())
//                .startDate(searchCriteriaDTO.getStartDate())
//                .endDate(searchCriteriaDTO.getEndDate())
//                .hotel(searchCriteriaDTO.getHotel())
//                .build();
//        infoRepository.save(infoEntity2);
        Date startDate = searchCriteriaDTO.getStartDate();
        Date endDate = searchCriteriaDTO.getEndDate();

        if(startDate.after(endDate) || ((endDate.getTime() - startDate.getTime()) / 1000 / 60 / 60 / 24) > 7) {
            // TODO: throw invalid dates exception
            return null;
        }

        // retrieve from database information based on clients' search criteria
        InfoEntity infoEntity = infoRepository.findByCityAndLatAndLonAndStartDateAndEndDateAndHotel(
                searchCriteriaDTO.getCity(),
                searchCriteriaDTO.getLat(),
                searchCriteriaDTO.getLon(),
                searchCriteriaDTO.getStartDate(),
                searchCriteriaDTO.getEndDate(),
                searchCriteriaDTO.getHotel());

        Map<String, BigDecimal> temperatureByDay = new HashMap<String, BigDecimal>();

        // if not info retrieved from database, then retrieve from external APIs and store the results in the database
        if(infoEntity == null) {
            // TODO: call weather API in order to obtain weather information based on clients' search criteria
            // https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&start_date=2022-10-30&end_date=2022-11-06&daily=temperature_2m_max&timezone=GMT
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String uri = "https://api.open-meteo.com/v1/forecast?latitude=" + searchCriteriaDTO.getLat() + "&longitude=" + searchCriteriaDTO.getLon() + "&start_date=" + dateFormat.format(searchCriteriaDTO.getStartDate()) + "&end_date=" + dateFormat.format(searchCriteriaDTO.getEndDate()) + "&daily=temperature_2m_max&timezone=GMT";
            System.out.println("Weather API URI: " + uri);

            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(uri, String.class);

            System.out.println("Weather API response: " + result);

            JSONObject json = (JSONObject) (new JSONObject(result)).get("daily");
            JSONArray dateArray = json.getJSONArray("time");
            JSONArray temperatureArray = json.getJSONArray("temperature_2m_max");

            List<Object> dateList = dateArray.toList();
            List<Object> temperatureList = temperatureArray.toList();

            for(int i = 0; i < dateList.size(); i ++) {
                temperatureByDay.put((String) dateList.get(i), (BigDecimal) temperatureList.get(i));
            }

            // TODO: call review API in order to obtain reviews of other clients for the location mentioned in the clients' search criteria
            // TODO: store both weather info and reviews along with clients' search criteria in database

            infoEntity = InfoEntity.builder()
                    .city(searchCriteriaDTO.getCity())
                    .lat(searchCriteriaDTO.getLat())
                    .lon(searchCriteriaDTO.getLon())
                    .startDate(searchCriteriaDTO.getStartDate())
                    .endDate(searchCriteriaDTO.getEndDate())
                    .hotel(searchCriteriaDTO.getHotel())
                    .build();
        }

        // prepare info data to send to client
        InfoDTO infoDto = InfoDTO.builder()
                .city(infoEntity.getCity())
                .startDate(infoEntity.getStartDate())
                .endDate(infoEntity.getEndDate())
                .hotel(infoEntity.getHotel())
                .temperatureByDay(temperatureByDay)
                .build();

        return infoDto;
    }

}
