package kopo.poly.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.TripChoiceDTO;
import kopo.poly.repository.TravelChoiceRepository;
import kopo.poly.repository.TravelRepository;
import kopo.poly.repository.entity.TravelChoiceEntity;
import kopo.poly.repository.entity.TravelEntity;
import kopo.poly.service.ITravelService;
import kopo.poly.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service("TravelService")
public class TravelService implements ITravelService {

    private final TravelRepository travelRepository;
    private final TravelChoiceRepository travelChoiceRepository;

    @Override
    public int insertUserChoiceTravel(List<TripChoiceDTO> tripChoiceDTOList) throws Exception {
        log.info(this.getClass().getName()+"회원 구독 여행지 저장 시작!");

        int res = 0;
        TravelEntity travelEntity = TravelEntity.builder()
                .startDay(tripChoiceDTOList.get(0).getStartDay()).endDay(tripChoiceDTOList.get(0).getEndDay())
                .title(tripChoiceDTOList.get(0).getTitle()).pCnt(tripChoiceDTOList.get(0).getPCnt())
                .regId(tripChoiceDTOList.get(0).getRegId()).regDt(DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss")).build();


        travelRepository.save(travelEntity);

        TravelEntity travel = travelRepository.findByTitle(tripChoiceDTOList.get(0).getTitle());

        for (TripChoiceDTO tripChoiceDTO : tripChoiceDTOList) {

            TravelChoiceEntity travelChoiceEntity = TravelChoiceEntity.builder()
                    .travelTitle(tripChoiceDTO.getTravelTitle()).addr1(tripChoiceDTO.getAddr1())
                    .mapX(tripChoiceDTO.getMapX()).mapY(tripChoiceDTO.getMapY())
                    .firstImage(tripChoiceDTO.getFirstImage()).firstImage2(tripChoiceDTO.getFirstImage2())
                    .regId(tripChoiceDTO.getRegId()).regDt(DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss"))
                    .travelSeq(travel.getTravelSeq()).build();
        }

        res = 1;

        log.info(this.getClass().getName() + "회원 구독 여행지 저장 종료!");
        return res;
    }

    @Override
    public List<TripChoiceDTO> getTravel(String userId) throws Exception {
        log.info(this.getClass().getName()+"회원 구독여행지 부모 테이블 조회 시작!");


        List<TravelEntity> travelEntityList = travelRepository.findAllByRegId(userId);

        List<TripChoiceDTO> tripChoiceDTOList = new ObjectMapper().convertValue(travelEntityList, new TypeReference<List<TripChoiceDTO>>() {
        });

        if (tripChoiceDTOList == null) {
            tripChoiceDTOList = new ArrayList<>();
        }

        log.info(this.getClass().getName()+"회원 구독여행지 부모 테이블 조회 종료!");
        return tripChoiceDTOList;
    }

    @Override
    public List<TripChoiceDTO> getTravelChoice(TripChoiceDTO tripChoiceDTO) throws Exception {
        log.info(this.getClass().getName()+"회원 구독여행지 상세보기 시작!");


        List<TravelChoiceEntity> travelChoiceEntityList = travelChoiceRepository.findAllByTravelSeqAndRegId(Long.parseLong(tripChoiceDTO.getTravelSeq()), tripChoiceDTO.getRegId());

        List<TripChoiceDTO> tripChoiceDTOList = new ObjectMapper().convertValue(travelChoiceEntityList, new TypeReference<List<TripChoiceDTO>>() {
        });

        if (tripChoiceDTOList == null) {
            tripChoiceDTOList = new ArrayList<>();
        }
        log.info(this.getClass().getName()+"회원 구독여행지 상세보기 종료!");
        return tripChoiceDTOList;
    }
}
