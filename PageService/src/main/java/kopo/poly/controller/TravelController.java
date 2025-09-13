package kopo.poly.controller;

import kopo.poly.auth.JwtTokenProvider;
import kopo.poly.auth.JwtTokenType;
import kopo.poly.dto.TravelDTO;
import kopo.poly.dto.TravelResultDTO;
import kopo.poly.dto.TripChoiceDTO;
import kopo.poly.service.ITravelService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping
@RequiredArgsConstructor
@Controller("/travel")
public class TravelController {

    private final ITravelService travelService;
    private final JwtTokenProvider jwtTokenProvider;
    private String title = "";
    private String state = "";
    private String msg = "";
    private String url = "";
    private String redirect = "redirect";

    @GetMapping("koreaTravel")
    public String koreaTravel(TravelDTO travelDTO, Model model) throws Exception {
        log.info(this.getClass().getName() + "국내 관광지 조회 페이지 시작!");

        List<TravelResultDTO> rList = travelService.locTravel(travelDTO);

        if (rList == null) {
            rList = new ArrayList<>();
        }

        model.addAttribute("rList", rList);
        return "travel/koreaTravel";
    }

    @GetMapping("koreaTravelDetail")
    public String  koreaTravelDetail(TravelDTO travelDTO, Model model)throws Exception {
        log.info(this.getClass().getName()+"국내 관광지 디테일 시작!");


        TravelResultDTO commonTravel = travelService.commonTravel(travelDTO);
        TravelResultDTO reTravel = travelService.reTravel(travelDTO);
        TravelResultDTO introduceTravel = travelService.introduceTravel(travelDTO);

        if (commonTravel == null) {
            commonTravel = new TravelResultDTO();
        }

        if (reTravel == null) {
            reTravel = new TravelResultDTO();
        }

        if (introduceTravel == null) {
            introduceTravel = new TravelResultDTO();
        }

        model.addAttribute("commonTravel", commonTravel);
        model.addAttribute("reTravel", reTravel);
        model.addAttribute("introduceTravel", introduceTravel);

        log.info(this.getClass().getName()+"국내 관광지 디테일 종료!");

        return "travel/travelDetail";
    }

    @GetMapping("travelChoice")
    public String travelChoice() {
        log.info(this.getClass().getName() + "회원 여행지 구독 페이지 시작!");

        return "travel/travelChoice";
    }

    @GetMapping("travelChoiceResult")
    public String travelChoiceResult()throws Exception{
        log.info(this.getClass().getName()+"회원 여행지 구독 페이지 시작!");

        return "/travel/travelResult";
    }

    @PostMapping("travelChoiceProc")
    public String travelChoiceProc(HttpServletRequest request, Model model)throws Exception {
        log.info(this.getClass().getName()+"회원 여행지 구독 시작!");

        String token = CmmUtil.nvl(jwtTokenProvider.resolveToken(request, JwtTokenType.REFRESH_TOKEN));
        String userId = CmmUtil.nvl(jwtTokenProvider.getUserId(token));

        String title = CmmUtil.nvl(request.getParameter("title"));
        String startDay = CmmUtil.nvl(request.getParameter("startDay"));
        String endDay = CmmUtil.nvl(request.getParameter("endDay"));
        String pCnt = CmmUtil.nvl(request.getParameter("pCnt"));

        String[] travelChoiceTitle = request.getParameterValues("travelChoiceTitle");
        String[] addr1 = request.getParameterValues("addr1");
        String[] mapX = request.getParameterValues("mapX");
        String[] mapY = request.getParameterValues("mapY");
        String[] firstImage = request.getParameterValues("firstImage");
        String[] firstImage2 = request.getParameterValues("firstImage2");

        TripChoiceDTO tripChoiceDTO = null;
        List<TripChoiceDTO> tripChoiceDTOList = new ArrayList<>();

        for (int i = 0; i < travelChoiceTitle.length; i++){

            tripChoiceDTO = new TripChoiceDTO();

            tripChoiceDTO.setTitle(title);
            tripChoiceDTO.setStartDay(startDay);
            tripChoiceDTO.setEndDay(endDay);
            tripChoiceDTO.setPCnt(pCnt);
            tripChoiceDTO.setTravelTitle(travelChoiceTitle[i]);
            tripChoiceDTO.setAddr1(addr1[i]);
            tripChoiceDTO.setMapX(mapX[i]);
            tripChoiceDTO.setMapY(mapY[i]);
            tripChoiceDTO.setFirstImage(firstImage[i]);
            tripChoiceDTO.setFirstImage2(firstImage2[i]);
            tripChoiceDTO.setRegId(userId);
            tripChoiceDTO.setRegDt(DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss"));

            tripChoiceDTOList.add(tripChoiceDTO);
        }


        int res = travelService.insertUserChoiceTravel(tripChoiceDTOList);

        log.info(this.getClass().getName()+"회원 여행지 구독 종료!");


        if (res == 1) {
            model.addAttribute("title", "여행지 생성 성공");
            model.addAttribute("msg", "여행경로 생성에 성공했습니다!");
            model.addAttribute("state", "success");
            model.addAttribute("url", "/");
        }else{
            model.addAttribute("title", "여행지 생성 실패");
            model.addAttribute("msg", "여행경로 생성에 실패했습니다!");
            model.addAttribute("state", "warning");
            model.addAttribute("url", "/travel/travelChoice");
        }
        return redirect;
    }




}
