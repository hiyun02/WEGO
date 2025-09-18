package kopo.poly.controller;

import kopo.poly.auth.JwtTokenProvider;
import kopo.poly.auth.JwtTokenType;
import kopo.poly.dto.TravelResultDTO;
import kopo.poly.dto.UserDTO;
import kopo.poly.service.IMainService;
import kopo.poly.service.IS3UploadService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Slf4j
@RequestMapping("/main")
@RequiredArgsConstructor
@Controller
public class MainController {

    private final IMainService mainService;
    private final JwtTokenProvider jwtTokenProvider;
    private final IS3UploadService s3UploadService;

    private String title = "";
    private String state = "";
    private String msg = "";
    private String url = "";
    private String redirect = "redirect";

    @GetMapping("main")
    public String main(Model model, HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + "메인 페이지 시작");

        /*임시 계정 정보*/
        String token = CmmUtil.nvl(jwtTokenProvider.resolveToken(request, JwtTokenType.REFRESH_TOKEN));
        String userId = CmmUtil.nvl(jwtTokenProvider.getUserId(token));
        String userName = CmmUtil.nvl(jwtTokenProvider.getUserName(token));


        List<TravelResultDTO> rList = mainService.travelMain(userId);

        model.addAttribute("model", model);

        model.addAttribute("user_name", userName);
        return "main/main";
    }

    @GetMapping("userInfoChg")
    public String userInfoChg(HttpServletRequest request, Model model) throws Exception {
        log.info(this.getClass().getName() + "회원 수정페이지 시작");

        String token = CmmUtil.nvl(jwtTokenProvider.resolveToken(request, JwtTokenType.REFRESH_TOKEN));
        String userId = CmmUtil.nvl(jwtTokenProvider.getUserId(token));

        UserDTO userDTO = mainService.getUserInfo(userId);

        model.addAttribute("userDTO", userDTO);

        //TODO 파일명 변경 == userInfoChg, main폴더 이동
        log.info(this.getClass().getName() + " 회원 수정페이지 종료");

        return "main/userInfoChg";
    }

    @GetMapping("userPwdChg")
    public String userPwdChg() throws Exception{
        log.info(this.getClass().getName() + ".userPwdChg Start !!");
        log.info(this.getClass().getName() + ".userPwdChg End !!");
        return "main/userPwdChg";
    }

    @PostMapping("userInfoChgProc")
    public String userInfoChgProc(HttpServletRequest request,UserDTO userDTO, Model model, @RequestParam(value = "fileUpload") MultipartFile mf)throws Exception {
        log.info(this.getClass().getName()+"회원정보 변경 시작!");


        /*임시 계정 정보*/
        String token = CmmUtil.nvl(jwtTokenProvider.resolveToken(request, JwtTokenType.REFRESH_TOKEN));
        String user_id;
        try {
            user_id = CmmUtil.nvl(jwtTokenProvider.getUserId(token));
        } catch (Exception e) {
            user_id = "";
        }
        userDTO.setUserId(user_id);
        log.info("user_id : " + userDTO.getUserId());
        String saveFilePath = s3UploadService.fileUpload(mf, userDTO.getUserId());
        log.info("saveFilePath : " + saveFilePath);
        userDTO.setFilePath(saveFilePath);

        int res = mainService.updateUserInfo(userDTO);

        if (res == 1) {
            title = "회원정보 수정";
            state = "success";
            msg = "회원정보 수정을 완료했습니다 :)";
            url = "/user/login";
        } else {
            title = "회원정보 수정";
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
}
