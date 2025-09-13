package kopo.poly.controller;

import kopo.poly.auth.JwtTokenProvider;
import kopo.poly.auth.JwtTokenType;
import kopo.poly.dto.UserDTO;
import kopo.poly.service.IUserService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserController {

    private String title = "";
    private String state = "";
    private String msg = "";
    private String url = "";
    private String redirect = "redirect";
    private final IUserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    @GetMapping("login")
    public String userLogin() {
        log.info(this.getClass().getName() + "로그인 페이지 시작!");
        return "/user/login";
    }


    @GetMapping("loginFail")
    public String loginFail(Model model) {
        log.info(this.getClass().getName() + "로그인 실패!");

        model.addAttribute("title", "로그인 실패");
        model.addAttribute("msg", "아이디와 비밀번호를 다시 확인해 주세요 :(");
        model.addAttribute("state", "false");
        model.addAttribute("url", "/user/login");

        return redirect;
    }

    @GetMapping("logOut")
    public String logOut(Model model) {
        log.info(this.getClass().getName() + "로그아웃!");

        model.addAttribute("title", "로그아웃");
        model.addAttribute("msg", "로그아웃 되었습니다 :)");
        model.addAttribute("state", "success");
        model.addAttribute("url", "/user/login");

        return redirect;
    }

    @GetMapping("findId")
    public String userFindId() {
        log.info(this.getClass().getName() + "아이디 찾기 페이지 시작!");
        return "/user/findId";
    }

    @PostMapping("findIdProc")
    public String userFindIdProc(UserDTO userDTO, Model model) throws Exception {
        log.info(this.getClass().getName() + "회원 아이디 찾기 시작!");

        String userId = userService.findUserId(userDTO);

        if (userDTO != null) {
            title = "아이디 찾기";
            state = "success";
            msg = "회원님의 아이디는 : " + userId + " 입니다!";
            url = "/user/login";
        } else {
            title = "아디이 찾기";
            state = "false";
            msg = "이름과 이메일을 다시한번 확인해 주세요";
            url = "/user/findId";
        }
        model.addAttribute("title", title);
        model.addAttribute("state", state);
        model.addAttribute("msg", msg);
        model.addAttribute("url", url);

        return redirect;
    }

    @GetMapping("findPwd")
    public String userFindPwd() {
        log.info(this.getClass().getName() + "회원 비밀번호 찾기 시작!");
        return "/user/findPwd";
    }

    @GetMapping("findPwdProc")
    public String findPwdProc(UserDTO userDTO, Model model) throws Exception {
        log.info(this.getClass().getName() + "회운 비밀번호찾기 시작!");

        int res = userService.findUserPwd(userDTO);


        if (res == 1) {
            title = "비밀번호 변경";
            state = "success";
            msg = "비밀번호 변경에 성공했습니다. 다시 로그인해 주세요 :)";
            url = "/user/login";
        } else {
            title = "비밀번호 변경";
            state = "false";
            msg = "비밀번호 변경에 실패했습니다. 다시 입력해 주세요 :(";
            url = "/user/findPwd";
        }
        model.addAttribute("title", title);
        model.addAttribute("state", state);
        model.addAttribute("msg", msg);
        model.addAttribute("url", url);

        return redirect;
    }

    @PostMapping("chgPwdProc")
    public String chgPwdProc(HttpServletRequest request, Model model) throws Exception {
        log.info(this.getClass().getName() + "비밀번호 변경 시작!");


        String token = CmmUtil.nvl(jwtTokenProvider.resolveToken(request, JwtTokenType.REFRESH_TOKEN));
        String userId = CmmUtil.nvl(jwtTokenProvider.getUserId(token));

        log.info("userId : " + userId);
        String password = CmmUtil.nvl(request.getParameter("pwd"));
        log.info("pwd : " + password);
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword(password);
        userDTO.setUserId(userId);

        int res = userService.findUserPwd(userDTO);


        if (res == 1) {
            title = "비밀번호 변경";
            state = "success";
            msg = "비밀번호 변경에 성공했습니다. 다시 로그인해 주세요 :)";
            url = "/user/login";
        } else {
            title = "비밀번호 변경";
            state = "false";
            msg = "비밀번호 변경에 실패했습니다. 다시 입력해 주세요 :(";
            url = "/user/findPwd";
        }
        model.addAttribute("title", title);
        model.addAttribute("state", state);
        model.addAttribute("msg", msg);
        model.addAttribute("url", url);

        return redirect;
    }

    @GetMapping("userReg")
    public String userReg() {
        log.info(this.getClass().getName() + "회원 가입 시작!");
        return "/user/userReg";
    }

    @PostMapping("userRegProc")
    public String userRegProc(UserDTO userDTO, Model model) throws Exception {
        log.info(this.getClass().getName() + "회원 가입 시작!");

        userDTO.setRoles("ROLE_USER");

        if (userDTO.getGender().equals("남자")) {
            userDTO.setFilePath("/user/man.png");
        } else {
            userDTO.setFilePath("/user/woman.png");
        }

        int res = userService.userReg(userDTO);

        if (res == 1) {
            title = "회원가입";
            state = "success";
            msg = "회원가입에 성공했습니다. 회원님의 관심정보를 등록해 주세요 :)";
            url = "/user/interest?userName="+userDTO.getUserId();
        } else {
            title = "회원가입";
            state = "false";
            msg = "회원가입에 실패했습니다 다시 시도해 주세요 :(";
            url = "/user/userReg";
        }

        model.addAttribute("title", title);
        model.addAttribute("state", state);
        model.addAttribute("msg", msg);
        model.addAttribute("url", url);

        return redirect;
    }

    @GetMapping("interest")
    public String interest(HttpServletRequest request, Model model) {
        log.info(this.getClass().getName() + "회원 관심정보 입력 페이지");
        String userId = request.getParameter("userName");

        model.addAttribute("userId", userId);
        return "user/interest";
    }

    @PostMapping("userInterestProc")
    public String userInterestProc(HttpServletRequest request, Model model)throws Exception {
        log.info(this.getClass().getName() + "회원 관심정보 입력 시작");

        String interest[] = request.getParameterValues("interest");

        String userId = request.getParameter("userId");
        int res = userService.userInterest(interest, userId);

        if (res == 1) {
            title = "관심정보 저장";
            state = "success";
            msg = "관심사 저장을 완료했습니다 :)";
            url = "/user/login";
        } else {
            title = "관심정보 저장";
            state = "false";
            msg = "관심사 저장에 실했습니다 다시 시도해주세요 :(";
            url = "/user/interest";
        }

        model.addAttribute("title", title);
        model.addAttribute("state", state);
        model.addAttribute("msg", msg);
        model.addAttribute("url", url);

        return redirect;

    }

}
