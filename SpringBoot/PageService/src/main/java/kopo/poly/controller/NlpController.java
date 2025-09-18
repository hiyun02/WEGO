package kopo.poly.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/nlp")
@RequiredArgsConstructor
@Controller
public class NlpController {

    //TODO 화면 설계하기

    @GetMapping("travelRecommend")
    public String travelRecommend()throws Exception{
        log.info(this.getClass().getName()+"회원 여행지 추천페이지 시작!");

        return "/nlp/travelRecommend";
    }

    @PostMapping("travelRecommend")
    public String travelRecommendResult()throws Exception{
        log.info(this.getClass().getName()+"회원 여행지 자연어 처리 결과 페이지");


        return "/nlp/travelRecommendResult";
    }
}
