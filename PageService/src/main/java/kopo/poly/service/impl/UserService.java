package kopo.poly.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.auth.AuthInfo;
import kopo.poly.dto.InterestDTO;
import kopo.poly.dto.UserDTO;
import kopo.poly.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service("UserService")
public class UserService implements IUserService {

    private final RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        log.info(this.getClass().getName() + "회원 로그인 서비스 시작!");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(userId, headers);


        ResponseEntity<UserDTO> response = restTemplate.exchange("http://localhost:11000/user/getUserInfo", HttpMethod.POST, entity, UserDTO.class);

        UserDTO userDTO = response.getBody();

        log.info(this.getClass().getName() + "회원 로그인 서비스 종료!");

        return new AuthInfo(userDTO);
    }

    @Override
    public String findUserId(UserDTO userDTO) throws Exception {
        log.info(this.getClass().getName() + "유저 아이디 찾기 서비스 시작!");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserDTO> request = new HttpEntity<>(userDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:11000/user/findUserId", HttpMethod.POST, request, String.class);

        return response.getBody();
    }

    @Override
    public int findUserPwd(UserDTO userDTO) throws Exception {
        log.info(this.getClass().getName() + "회원 비밀번호 찾기");

        int res = 0;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserDTO> request = new HttpEntity<>(userDTO, headers);

        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:11000/user/findUserPwd", HttpMethod.POST, request, Integer.class);

        return response.getBody();
    }

    @Override
    public int userReg(UserDTO userDTO) throws Exception {
        log.info(this.getClass().getName() + "회원가입 서비스 시작!");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserDTO> request = new HttpEntity<>(userDTO, headers);

        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:11000/user/userReg", HttpMethod.POST, request, Integer.class);

        return response.getBody();
    }

    @Override
    public int userIdCheck(String userId) throws Exception {
        log.info(this.getClass().getName() + "회원 아이디 중복확인 서비스 시작!");;

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("userId", userId);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:11000/user/userIdCheck", HttpMethod.POST, request, Integer.class);

        return response.getBody();
    }

    @Override
    public int userEmailCheck(String email) throws Exception {
        log.info(this.getClass().getName() + "회원 이메일 중복확인 서비스 시작!");

        int res = 0;

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("email", email);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:11000/user/userEmailCheck", HttpMethod.POST, request, Integer.class);

        return response.getBody();
    }

    @Override
    public int userInterest(String interest[], String userId) throws Exception {
        log.info(this.getClass().getName() + "회원 관심정보 입력 시작!");

        InterestDTO interestDTO = null;

        List<InterestDTO> interestDTOList = new ArrayList<>();

        for (int i = 0; i < interest.length; i++) {
            interestDTO = new InterestDTO();

            interestDTO.setInterestContent(interest[i]);
            interestDTO.setRegId(userId);

            interestDTOList.add(interestDTO);
        }

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<List<InterestDTO>> request = new HttpEntity<>(interestDTOList, headers);

        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:11000/user/userInterest", HttpMethod.POST, request, Integer.class);


        return response.getBody();
    }

    @Override
    public int userSendMail(UserDTO userDTO) throws Exception {
        log.info(this.getClass().getName() + "회원 인증 메일 전송 시작!");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserDTO> requset = new HttpEntity<>(userDTO, headers);

        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:11000/user/userMailSend", HttpMethod.POST, requset, Integer.class);

        return response.getBody();
    }



}
