package kopo.poly.service.impl;

import kopo.poly.dto.ApiResponseDTO;
import kopo.poly.dto.TravelDTO;
import kopo.poly.dto.TravelResultDTO;
import kopo.poly.dto.TripChoiceDTO;
import kopo.poly.service.ITravelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.xml.ws.Response;
import java.awt.geom.RectangularShape;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service("TravelService")
public class TravelService implements ITravelService {

    private final RestTemplate restTemplate;

    /*
    * 지역기반 관광정보 조회
    * @Param 주소
    * @Param
    * @Param
    * */
    @Override
    public List<TravelResultDTO> locTravel(TravelDTO travelDTO) throws Exception {
        log.info(this.getClass().getName()+"지역기반 관광 정보 조회 시작!");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(travelDTO, headers);

        ResponseEntity<ApiResponseDTO> response = restTemplate.exchange("http://localhost:11000/api/koreaTravel", HttpMethod.POST, entity, ApiResponseDTO.class);

        log.info(this.getClass().getName()+"지역기반 관광 정보 조회 종료!");
        return response.getBody().getTravelResultDTOList();
    }

    @Override
    public TravelResultDTO commonTravel(TravelDTO travelDTO) throws Exception {
        log.info(this.getClass().getName() + " 공통정보 조회 시작!");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(travelDTO, headers);

        ResponseEntity<TravelResultDTO> response = restTemplate.exchange("http://localhost:11000/api/commonTravel", HttpMethod.POST, entity, TravelResultDTO.class);

        log.info(this.getClass().getName() + " 공통정보 조회 종료!");
        return response.getBody();
    }

    @Override
    public TravelResultDTO reTravel(TravelDTO travelDTO) throws Exception {
        log.info(this.getClass().getName()+"반복 정보 조회 시작!");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(travelDTO, headers);

        ResponseEntity<TravelResultDTO> response = restTemplate.exchange("http://localhost:11000/api/reTravel", HttpMethod.POST, entity, TravelResultDTO.class);

        log.info(this.getClass().getName()+"반복 정보 조회 종료!");
        return response.getBody();
    }

    @Override
    public List<TravelResultDTO> listTravelReInfo(TravelDTO travelDTO) throws Exception {
        log.info(this.getClass().getName() + "리스트 반복정보 조회 시작!");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(travelDTO, headers);

        ResponseEntity<ApiResponseDTO> response = restTemplate.exchange("http://localhost:11000/api/listTravelReInfo", HttpMethod.POST, entity, ApiResponseDTO.class);

        log.info(this.getClass().getName() + "리스트 반복정보 조회 종료!");
        return response.getBody().getTravelResultDTOList();
    }

    @Override
    public TravelResultDTO introduceTravel(TravelDTO travelDTO) throws Exception {
        log.info(this.getClass().getName()+"소개 정보 조회 시작!");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(travelDTO, headers);

        ResponseEntity<TravelResultDTO> response = restTemplate.exchange("http://localhost:11000/api/ResponseEntity", HttpMethod.POST, entity, TravelResultDTO.class);


        log.info(this.getClass().getName()+"소개 정보 조회 시작!");
        return response.getBody();
    }

    @Override
    public List<TravelResultDTO> userChoiceTravel(List<TravelDTO> travelDTOList) throws Exception {

        log.info(this.getClass().getName()+"회원 여행지 선택 시작!");

        HttpHeaders headers = new HttpHeaders();

        HttpEntity entity = new HttpEntity(travelDTOList, headers);

        ResponseEntity<ApiResponseDTO> response = restTemplate.exchange("http://localhost:11000/api/userChoiceTravel", HttpMethod.POST, entity, ApiResponseDTO.class);

        log.info(this.getClass().getName()+"회원 여행지 선택 종료!");

        return response.getBody().getTravelResultDTOList();
    }

    @Override
    public int insertUserChoiceTravel(List<TripChoiceDTO> tripChoiceDTOList) throws Exception {
        log.info(this.getClass().getName() + "사용자 여행 구독 시작!");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(tripChoiceDTOList, headers);

        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:11000/travel/insertUserChoiceTravel", HttpMethod.POST, entity, Integer.class);

        log.info(this.getClass().getName() + "사용자 여행 구독 종료!");
        return response.getBody();
    }
}
