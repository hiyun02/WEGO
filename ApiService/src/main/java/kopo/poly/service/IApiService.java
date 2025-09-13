package kopo.poly.service;

import com.sun.javafx.scene.traversal.TraverseListener;
import kopo.poly.dto.InterestDTO;
import kopo.poly.dto.TravelDTO;
import kopo.poly.dto.TravelResultDTO;
import kopo.poly.dto.TripChoiceDTO;

import java.util.List;

public interface IApiService {


    /* 사용자 관심정보 기반 키워드 조회 결과 == Main 페이지 결과 리턴*/
    List<TravelResultDTO> mainPageTravel(List<InterestDTO> interestDTOList) throws Exception;

    /* 지역기반 관광정보 조회*/
    List<TravelResultDTO> locTravel(TravelDTO travelDTO) throws Exception;

    /*관광데이터 공통정보 조회*/
    TravelResultDTO commonTravel(TravelDTO travelDTO) throws Exception;

    /* 관광데이터 반복정보 조회 */
    TravelResultDTO reTravel(TravelDTO travelDTO) throws Exception;

    /* 관광데이터 소개정보 조회 */
    TravelResultDTO introduceTravel(TravelDTO travelDTO) throws Exception;

    /* 관광데이터 리스트 반복정보 조회*/
    List<TravelResultDTO> listTravelReInfo(TravelDTO travelDTO) throws Exception;

    /*회원 여행 선택을 위한 관광지 선택*/
    List<TravelResultDTO> userChoiceTravel(List<TravelDTO> travelDTOList) throws Exception;




}
