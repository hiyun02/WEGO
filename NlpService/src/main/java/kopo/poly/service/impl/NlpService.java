package kopo.poly.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.NlpDTO;
import kopo.poly.dto.NlpDataDTO;
import kopo.poly.repository.NlpRepository;
import kopo.poly.repository.entity.NlpEntity;
import kopo.poly.service.INlpService;
import kopo.poly.util.UrlUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@EnableScheduling
@Service("NlpService")
public class NlpService implements INlpService {

    private NlpRepository nlpRepository;

    @Scheduled(cron = "0 0 1 * * *")    // 매일 00시 정각
    @Override
    public void getNlpData(List<NlpDataDTO> nlpDataDTOList) throws Exception {
        log.info(this.getClass().getName() + "자연어 처리 게시물 자연어 처리 후 DB Insert");

        JSONObject obj = new JSONObject();
        //List<Board_PostDTO> 변환을 위한 arry json 객체 생성
        JSONArray jsonArray = new JSONArray();

        for (NlpDataDTO nlpDataDTO : nlpDataDTOList) {
            JSONObject object = new JSONObject();
            object.put("saveFilePath", nlpDataDTO.getSaveFilePath());
            object.put("content", nlpDataDTO.getContent());
            object.put("place", nlpDataDTO.getPlace());
            object.put("location", nlpDataDTO.getLocation());
            object.put("city", nlpDataDTO.getCity());

            //배열에 obj값 담아서 끝내기
            jsonArray.add(object);
        }

        UrlUtil uu = new UrlUtil();

        String url = "http://192.168.0.117:8000";
        String api = "/nlp_jsonDataInfo";
        String nData = "?data=";
        String data = jsonArray.toJSONString();
        String fullPath = uu.urlReadforString(url + api + nData + URLEncoder.encode(data, "UTF-8"));

        log.info("전달받은 값은?" + fullPath);

        JSONParser parser = new JSONParser();
        JSONArray res_jsonArray = (JSONArray) parser.parse(fullPath);
        log.info(String.valueOf(res_jsonArray.size()));

        log.info("jsonarray로 변환한 결과 값은 ? " + res_jsonArray);

        for (int i = 0; i < res_jsonArray.size(); i++) {
            JSONObject object = (JSONObject) res_jsonArray.get(i);

            NlpEntity nlpEntity = NlpEntity.builder()
                    .location((String) object.get("location")).city((String) object.get("city"))
                    .place((String) object.get("place")).saveFilePath((String) object.get("saveFilePath"))
                    .content((String) object.get("content")).result((String) object.get("result"))
                    .build();

            nlpRepository.save(nlpEntity);
        }
        log.info(this.getClass().getName() + "자연어 처리 종료!");
    }

    @Override
    public List<NlpDTO> getNlpRecommend(NlpDataDTO nlpDataDTO) throws Exception {

        log.info(this.getClass().getName() + "자연어 처리 추천 여행지 조회 시작!");

        List<NlpEntity> nlpEntityList = nlpRepository.findAllByLocationAndCity(nlpDataDTO.getLocation(), nlpDataDTO.getCity());

        if (nlpEntityList == null) {
            nlpEntityList = new ArrayList<NlpEntity>();
        }

        List<NlpDTO> nlpDTOList = new ArrayList<>();

        if (nlpEntityList != null) {
            nlpDTOList = new ObjectMapper().convertValue(nlpEntityList, new TypeReference<List<NlpDTO>>() {
            });
        }

        if (nlpDTOList != null) {
            Collections.sort(nlpDTOList, new Comparator<NlpDTO>() {
                @Override
                public int compare(NlpDTO o1, NlpDTO o2) {
                    return Integer.compare(Integer.parseInt(o2.getResult()), Integer.parseInt(o1.getResult()));
                }
            });
        }

        log.info(this.getClass().getName() + "자연어 처리 추천 여행지 조회 종료!");

        return nlpDTOList;
    }
}
