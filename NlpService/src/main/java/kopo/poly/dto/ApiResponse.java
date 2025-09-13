package kopo.poly.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse {

    private List<NlpDataDTO> nlpDataDTOList;
}
