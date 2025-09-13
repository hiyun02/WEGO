package kopo.poly.controller;

import kopo.poly.auth.JwtTokenProvider;
import kopo.poly.auth.JwtTokenType;
import kopo.poly.dto.InterestDTO;
import kopo.poly.dto.UserDTO;
import kopo.poly.service.IUserService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserRestController {
    private final IUserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping("findUserId")
    public ResponseEntity<String> findUserId(@RequestBody UserDTO userDTO) throws Exception {
        log.info(this.getClass().getName() + "회원 아이디 찾기 API 시작!");

        String userId = userService.findUserId(userDTO);

        return ResponseEntity.ok().body(userId);
    }

    @PostMapping("findUserPwd")
    public ResponseEntity<Integer> findUserPwd(@RequestBody UserDTO userDTO) throws Exception {
        log.info(this.getClass().getName() + "회원 비밀번호 변경 시작!");

        int res = userService.findUserPwd(userDTO);

        return ResponseEntity.ok().body(res);
    }

    @PostMapping("userReg")
    public ResponseEntity<Integer> userReg(@RequestBody UserDTO userDTO) throws Exception {
        log.info(this.getClass().getName() + "회원가입 시작!");

        int res = userService.userReg(userDTO);

        return ResponseEntity.ok().body(res);
    }

    @PostMapping("userIdCheck")
    public ResponseEntity<Integer> userIdCheck(@RequestBody MultiValueMap<String, String> rMap) throws Exception {
        log.info(this.getClass().getName() + "유저 아이디 체크 시작");

        int res = userService.userIdCheck(rMap.getFirst("userId"));

        return ResponseEntity.ok().body(res);
    }

    @PostMapping("userEmailCheck")
    public ResponseEntity<Integer> userEmailCheck(@RequestBody MultiValueMap<String, String> rMap) throws Exception {
        log.info(this.getClass().getName() + "유저 이메일 체크 시작!");

        int res = userService.userEmailCheck(rMap.getFirst("email"));

        return ResponseEntity.ok().body(res);
    }

    @PostMapping("userInterest")
    public ResponseEntity<Integer> userInterest(@RequestBody List<InterestDTO> interestDTOList) throws Exception {
        log.info(this.getClass().getName() + "회원 관심정보 등록 시작!");

        int res = 0;

        res = userService.userInterest(interestDTOList);

        return ResponseEntity.ok().body(res);
    }

    @PostMapping("userMailSend")
    public ResponseEntity<Integer> userMailSend(@RequestBody UserDTO userDTO)throws Exception {
        log.info(this.getClass().getName() + "회원 인증메일 전송 시작!");

        int res = userService.userSendEmail(userDTO);

        return ResponseEntity.ok().body(res);
    }

    @PostMapping("getUserInfo")
    public ResponseEntity<UserDTO> getUserInfo(@RequestBody String userId)throws Exception{
        log.info(this.getClass().getName()+"회원정보 가져오기 시작!");

        UserDTO userDTO = userService.getUserInfo(userId);

        return ResponseEntity.ok().body(userDTO);
    }

    @PostMapping("updateUserInfo")
    public ResponseEntity<Integer> updateUserInfo(@RequestBody UserDTO userDTO) throws Exception {
        log.info(this.getClass().getName()+"회원 업데이트 시작!");

        int res = userService.updateUserInfo(userDTO);

        return ResponseEntity.ok().body(res);
    }

    @PostMapping("getUserInterest")
    public ResponseEntity<Map<String,List<InterestDTO>>> getUserInterest(@RequestBody String userId)throws Exception {
        log.info(this.getClass().getName() + "회원 업데이트 시작!");

        List<InterestDTO> interestDTOList = userService.getUserInterest(userId);


        if (interestDTOList == null) {
            interestDTOList = new ArrayList<>();
        }

        Map<String, List<InterestDTO>> rMap = new HashMap<>();
        rMap.put("interestDTOList", interestDTOList);
        return ResponseEntity.ok().body(rMap);
    }
}
