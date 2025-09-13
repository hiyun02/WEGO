package kopo.poly.service;

import kopo.poly.dto.InterestDTO;
import kopo.poly.dto.UserDTO;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService{

    // 회원 아이디 찾기
    String findUserId(UserDTO userDTO) throws Exception;

    // 회원 비밀번호 찾기
    int findUserPwd(UserDTO userDTO) throws Exception;

    //회원가입
    int userReg(UserDTO userDTO) throws Exception;

    // 아이디 중복 확인
    int userIdCheck(String userId) throws Exception;

    // 이메일 중복 확인
    int userEmailCheck(String email) throws Exception;

    // 회원 관심정보 저장
    int userInterest(List<InterestDTO> rList) throws Exception;

    // 회원 관심정보 조회
    List<InterestDTO> getUserInterest(String userId) throws Exception;

    // 회원 이메일 전송
    int userSendEmail(UserDTO userDTO) throws Exception;

    // 회원정보 조회
    UserDTO getUserInfo(String userId)throws Exception;

    //회원 정보 수정
    int updateUserInfo(UserDTO userDTO) throws Exception;



}
