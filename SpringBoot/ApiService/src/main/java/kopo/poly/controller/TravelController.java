package kopo.poly.controller;

import kopo.poly.dto.TripChoiceDTO;
import kopo.poly.service.ITravelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/travel")
@RequiredArgsConstructor
@RestController
public class TravelController {

    private final ITravelService travelService;

    @PostMapping("insertUserChoiceTravel")
    public ResponseEntity<Integer> insertUserChoiceTravel(@RequestBody List<TripChoiceDTO> tripChoiceDTOList) throws Exception{
        log.info(this.getClass().getName() + "회원 구독 여행지 저장");

        int res = travelService.insertUserChoiceTravel(tripChoiceDTOList);

        return ResponseEntity.ok().body(res);
    }
}
