package kopo.poly.controller.rest;

import kopo.poly.dto.UserDTO;
import kopo.poly.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserRestController {
    private final IUserService userService;


    @PostMapping("userIdCheck")
    public int userIdCheck(HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + "회원 아이디 중복 확인 시작!");

        int res = userService.userIdCheck(request.getParameter("userId"));

        return res;
    }

    @PostMapping("userEmailCheck")
    public int userEmailCheck(HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + "회원 이메일 중복 체크 시작!");

        int res = userService.userEmailCheck(request.getParameter("email"));

        return res;
    }

    @PostMapping("userSendMail")
    public int userSendMail(@RequestParam Map<String, String> rMap)throws Exception {
        log.info(this.getClass().getName() + "이메일 랜덤핀 전송");

        UserDTO userDTO = new UserDTO();

        userDTO.setUserId(rMap.get("userId"));
        userDTO.setEmail(rMap.get("email"));

        int res = userService.userSendMail(userDTO);

        return res;
    }
}
