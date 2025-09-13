package kopo.poly.controller;

import kopo.poly.auth.AuthInfo;
import kopo.poly.auth.JwtTokenProvider;
import kopo.poly.auth.JwtTokenType;
import kopo.poly.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequestMapping("/jwt")
@RequiredArgsConstructor
@Controller
public class JwtController {
    @Value("${jwt.token.access.valid.time}")
    private long accessTokenValidTime;

    @Value("${jwt.token.access.name}")
    private String accessTokenName;

    @Value("${jwt.token.refresh.valid.time}")
    private long refreshTokenValidTime;

    @Value("${jwt.token.refresh.name}")
    private String refreshTokenName;
    private final JwtTokenProvider jwtTokenProvider;

    private String title = "";
    private String state = "";
    private String msg = "";
    private String url = "";
    private String redirect = "redirect";

    @RequestMapping(value = "loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal AuthInfo authInfo,
                                       HttpServletResponse response, Model model) throws Exception {
        log.info(this.getClass().getName() + "유저 로그인 성공 로직 실행");

        // Spring Security 저장 정보 가져오기
        UserDTO userDTO = authInfo.getUserDTO();

        if (userDTO == null) {
            userDTO = new UserDTO();
        }

        // Access Token 생성
        String accessToken = jwtTokenProvider.createToken(userDTO.getUserId(), userDTO.getRoles(), JwtTokenType.ACCESS_TOKEN, userDTO.getUserName());

        ResponseCookie cookie = ResponseCookie.from(accessTokenName, accessToken)
                .domain("localhost")
                .path("/")
//                .secure(true)
//                .sameSite("None")
                .maxAge(accessTokenValidTime) // JWT Refresh Token 만료시간 설정
                .httpOnly(true)
                .build();

        // 기존 쿠기 모두 삭제하고, Cookie에 Access Token 저장하기
        response.setHeader("Set-Cookie", cookie.toString());

        cookie = null;

        // Refresh Token 생성
        // Refresh Token은 보안상 노출되면, 위험하기에 Refresh Token은 DB에 저장하고,
        // DB를 조회하기 위한 값만 Refresh Token으로 생성함
        // 본 실습은 DB에 저장하지 않고, 사용자 컴퓨터의 쿠키에 저장함
        // Refresh Token은 Access Token에 비해 만료시간을 길게 설정함
        String refreshToken = jwtTokenProvider.createToken(userDTO.getUserId(), userDTO.getRoles(), JwtTokenType.REFRESH_TOKEN, userDTO.getUserName());

        cookie = ResponseCookie.from(refreshTokenName, refreshToken)
                .domain("localhost")
                .path("/")
//                .secure(true)
//                .sameSite("None")
                .maxAge(refreshTokenValidTime) // JWT Refresh Token 만료시간 설정
                .httpOnly(true)
                .build();

        // 기존 쿠기에 Refresh Token 저장하기
        response.addHeader("Set-Cookie", cookie.toString());

        log.info(this.getClass().getName() + "유저 로그인 성공 로직 종료");


        model.addAttribute("title", "로그인 성공");
        model.addAttribute("msg", "환영합니다 회원님 :)");
        model.addAttribute("state", "success");
        model.addAttribute("url", "/main/main");

        return redirect;
    }

    @PostMapping(value = "loginFail")
    public String loginFail(Model model) {
        log.info(this.getClass().getName() + "로그인 실패");

        model.addAttribute("title", "로그인 실패");
        model.addAttribute("msg", "아이디와 비빌번호를 다시 확인해 주세요");
        model.addAttribute("state", "success");
        model.addAttribute("url", "/user/login");
        return redirect;

    }

    @GetMapping("logOut")
    public String logOut(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        log.info(this.getClass().getName()+"로그아웃 시작!");

        new SecurityContextLogoutHandler().logout(
                request, response, SecurityContextHolder.getContext().getAuthentication());

        model.addAttribute("title", "로그아웃");
        model.addAttribute("msg", "로그아웃 되었습니다.");
        model.addAttribute("state", "success");
        model.addAttribute("url", "/user/login");
        return redirect;
    }
}
