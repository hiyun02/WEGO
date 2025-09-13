package kopo.poly.service;

import kopo.poly.dto.InterestDTO;
import kopo.poly.dto.UserDTO;
import org.apache.tomcat.jni.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {

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

    // 관심정보 입력
    int userInterest(String interest[], String userId) throws Exception;

    // 인증메일 전송
    int userSendMail(UserDTO userDTO) throws Exception;

}
