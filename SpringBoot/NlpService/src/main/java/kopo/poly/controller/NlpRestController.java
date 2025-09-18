package kopo.poly.controller;

import kopo.poly.dto.ApiResponse;
import kopo.poly.dto.NlpDTO;
import kopo.poly.dto.NlpDataDTO;
import kopo.poly.service.INlpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/nlp")
@RestController
public class NlpRestController {

    private INlpService nlpService;
    private RestTemplate restTemplate;

    @PostMapping("getNlpData")
    public void getNlpData() {
        log.info(this.getClass().getName() + "게시판에서 자연어처리용 게시글 가져오기 시작");

        ResponseEntity<ApiResponse> response = restTemplate.exchange("", HttpMethod.POST, null, ApiResponse.class);

        log.info(this.getClass().getName() + "게시판에서 자연어처리용 게시글 가져오기 종료");
    }

    @PostMapping("getRecommenNlp")
    public ResponseEntity<List<NlpDTO>> getRecommendNlp(@RequestBody NlpDataDTO nlpDataDTO)throws Exception {
        log.info(this.getClass().getName()+"자연어 처리 추천 여행지 조회시작 ");

        List<NlpDTO> rList = nlpService.getNlpRecommend(nlpDataDTO);

        log.info(this.getClass().getName()+"자연어 처리 추천 여행지 조회 정료");

        return ResponseEntity.ok().body(rList);
    }
}
