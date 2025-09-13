package kopo.poly.controller;

import kopo.poly.auth.JwtTokenProvider;
import kopo.poly.auth.JwtTokenType;
import kopo.poly.dto.*;
import kopo.poly.service.impl.ApiService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ApiController {

    private final RestTemplate restTemplate;
    private final ApiService apiService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("mainKeyWord")
    public ResponseEntity<Map<String, List<TravelResultDTO>>> keyWord(@RequestBody String userId) throws Exception {
        log.info(this.getClass().getName() + "travelKeyWord 시작!");

        log.info("api 서비스에서 가져온 아이디 값 : " + userId);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity(userId, headers);

        /* 유저 관심정보 가져오기 */
        ResponseEntity<ApiResponse> response = restTemplate.exchange("http://localhost:11000/user/getUserInterest", HttpMethod.POST, entity, ApiResponse.class);

        List<InterestDTO> interestDTOList = response.getBody().getInterestDTOList();

        if (interestDTOList == null) {
            interestDTOList = new ArrayList<>();
        }

        List<TravelResultDTO> travelResultDTOList = apiService.mainPageTravel(interestDTOList);

        if (travelResultDTOList == null) {
            travelResultDTOList = new ArrayList<>();
        }

        Map<String, List<TravelResultDTO>> rMap = new HashMap<>();

        rMap.put("travelResultDTOList", travelResultDTOList);

        log.info("" + travelResultDTOList.get(0).getContentid());

        return ResponseEntity.ok().body(rMap);

    }

    @PostMapping("koreaTravel")
    public ResponseEntity<Map<String,List<TravelResultDTO>>> locTravel(@RequestBody TravelDTO travelDTO) throws Exception {
        log.info(this.getClass().getName() + "국내 여행지 조회 시작!");

        List<TravelResultDTO> travelResultDTOList = apiService.locTravel(travelDTO);

        if (travelResultDTOList == null) {
            travelResultDTOList = new ArrayList<>();
        }

        Map<String, List<TravelResultDTO>> rMap = new HashMap<>();

        rMap.put("travelResultDTOList", travelResultDTOList);

        log.info(this.getClass().getName() + "국내 여행지 조회 종료!");
        return ResponseEntity.ok().body(rMap);
    }

    @PostMapping("commonTravel")
    public ResponseEntity<TravelResultDTO> commonTravel(@RequestBody TravelDTO travelDTO) throws Exception {

        log.info(this.getClass().getName() + "공통정보 조회!");

        TravelResultDTO travelResultDTO = apiService.commonTravel(travelDTO);

        if (travelResultDTO == null) {
            travelResultDTO = new TravelResultDTO();
        }

        return ResponseEntity.ok().body(travelResultDTO);

    }

    @PostMapping("reTravel")
    private ResponseEntity<TravelResultDTO> reTravel(@RequestBody TravelDTO travelDTO) throws Exception {
        log.info(this.getClass().getName() + "반복 정보 조회");

        TravelResultDTO travelResultDTO = apiService.reTravel(travelDTO);

        return ResponseEntity.ok().body(travelResultDTO);
    }

    @PostMapping("introduceTravel")
    public ResponseEntity<TravelResultDTO> introduceTravel(@RequestBody TravelDTO travelDTO) throws Exception {
        log.info(this.getClass().getName() + "소개 정보 조회 시작!");

        TravelResultDTO travelResultDTO = apiService.introduceTravel(travelDTO);

        return ResponseEntity.ok().body(travelResultDTO);
    }

    @PostMapping("listTravelReInfo")
    public ResponseEntity<Map<String,List<TravelResultDTO>>> listTravelReInfo(@RequestBody TravelDTO travelDTO) throws Exception {
        log.info(this.getClass().getName() + " 리스트 반복정보 조회");

        List<TravelResultDTO> travelResultDTOList = apiService.listTravelReInfo(travelDTO);

        Map<String, List<TravelResultDTO>> rMap = new HashMap<>();

        rMap.put("travelResultDTOList", travelResultDTOList);

        return ResponseEntity.ok().body(rMap);
    }

    @PostMapping("userChoiceTravel")
    public ResponseEntity<Map<String,List<TravelResultDTO>>> userChoiceTravel(@RequestBody List<TravelDTO> travelDTOList) throws Exception {
        log.info(this.getClass().getName() + "회원 여행지 선택 시작");

        List<TravelResultDTO> travelResultDTOList = apiService.userChoiceTravel(travelDTOList);

        Map<String, List<TravelResultDTO>> rMap = new HashMap<>();

        rMap.put("travelResultDTOList", travelResultDTOList);

        return ResponseEntity.ok().body(rMap);

    }


}
