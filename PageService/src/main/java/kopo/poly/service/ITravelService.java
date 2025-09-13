package kopo.poly.service;

import kopo.poly.dto.TravelDTO;
import kopo.poly.dto.TravelResultDTO;
import kopo.poly.dto.TripChoiceDTO;

import java.util.List;

public interface ITravelService {

    /* 지역기반 정보 조회하기 */
    List<TravelResultDTO> locTravel(TravelDTO travelDTO) throws Exception;

    /* 공통정보 조회하기 */
    TravelResultDTO commonTravel(TravelDTO travelDTO) throws Exception;

    /* 반복정보 조회 하기*/
    TravelResultDTO reTravel(TravelDTO travelDTO) throws Exception;

    /* 리스트 반복정보 조회하기 */
    List<TravelResultDTO> listTravelReInfo(TravelDTO travelDTO) throws Exception;

    /* 소개정보 조회하기 */
    TravelResultDTO introduceTravel(TravelDTO travelDTO) throws Exception;

    /* 회원 여행지 선택하기 */
    List<TravelResultDTO> userChoiceTravel(List<TravelDTO> travelDTOList) throws Exception;

    /* 회원 구독 여행지 저장 */
    int insertUserChoiceTravel(List<TripChoiceDTO> tripChoiceDTOList) throws Exception;

}
