package kopo.poly.service;

import kopo.poly.dto.NlpDTO;
import kopo.poly.dto.NlpDataDTO;

import java.util.List;

public interface INlpService {

    void getNlpData(List<NlpDataDTO> nlpDataDTOList) throws Exception;

    List<NlpDTO> getNlpRecommend(NlpDataDTO nlpDataDTO) throws Exception;

}
