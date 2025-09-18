package kopo.poly.service;

import kopo.poly.dto.TripChoiceDTO;


import java.util.List;


public interface ITravelService {

    /* 회원 관광지 구독 */
    int insertUserChoiceTravel(List<TripChoiceDTO> tripChoiceDTOList) throws Exception;

    /* 회원 구독 여행지 부모 가져오기*/
    List<TripChoiceDTO> getTravel(String userId) throws Exception;

    /* 회원 구독 여행지 상세 보기 */
    List<TripChoiceDTO> getTravelChoice(TripChoiceDTO tripChoiceDTO) throws Exception;
}
