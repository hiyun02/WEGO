package kopo.poly.service;

import kopo.poly.dto.TravelResultDTO;
import kopo.poly.dto.UserDTO;

import java.util.List;

public interface IMainService {

    /* 메인 페이지 조회 시 사용자 관심정보에 따른 여행지 가져오기*/
    List<TravelResultDTO> travelMain(String userId) throws Exception;

    /* 회원정보 가져오기 */
    UserDTO getUserInfo(String userId) throws Exception;

    /* 회원정보 업데이트 */
    int updateUserInfo(UserDTO userDTO) throws Exception;
}
