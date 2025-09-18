package kopo.poly.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDTO {

    private List<TravelResultDTO> travelResultDTOList;
}
