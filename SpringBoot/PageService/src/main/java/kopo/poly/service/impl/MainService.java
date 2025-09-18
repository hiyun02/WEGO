package kopo.poly.service.impl;

import kopo.poly.dto.ApiResponseDTO;
import kopo.poly.dto.TravelResultDTO;
import kopo.poly.dto.UserDTO;
import kopo.poly.service.IMainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service("MainService")
@RequiredArgsConstructor
public class MainService implements IMainService {

    private final RestTemplate restTemplate;

    @Override
    public List<TravelResultDTO> travelMain(String userId) throws Exception {
        log.info(this.getClass().getName()+"메인 페이지 여행 API 호출 시작!");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(userId, headers);

        ResponseEntity<ApiResponseDTO> response = restTemplate.exchange("http://localhost:11000/api/mainKeyWord", HttpMethod.POST, entity, ApiResponseDTO.class);

        List<TravelResultDTO> travelResultDTOList = response.getBody().getTravelResultDTOList();

        log.info(this.getClass().getName()+"메인 페이지 여행 API 호출 종료!");
        return travelResultDTOList;
    }

    @Override
    public int updateUserInfo(UserDTO userDTO)throws Exception {
        log.info(this.getClass().getName() + "회원 업데이트 시작!");

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<UserDTO> request = new HttpEntity<>(userDTO, headers);

        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:11000/user/updateUserInfo", HttpMethod.POST, request, Integer.class);

        log.info(this.getClass().getName() + "회원 업데이트 종료!");

        return response.getBody();
    }

    @Override
    public UserDTO getUserInfo(String userId)throws Exception {
        log.info(this.getClass().getName()+"회원정보 가져오기 시작!");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requset = new HttpEntity<>(userId, headers);

        ResponseEntity<UserDTO> response = restTemplate.exchange("http://localhost:11000/user/getUserInfo", HttpMethod.POST, requset, UserDTO.class);

        log.info(this.getClass().getName()+"회원정보 가져오기 종료!");

        return response.getBody();
    }
}
