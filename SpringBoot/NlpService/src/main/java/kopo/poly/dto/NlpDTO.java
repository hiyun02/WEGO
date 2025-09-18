package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NlpDTO {

    private Long nlpSeq;
    private String city;
    private String place;
    private String location;
    private String content;
    private String result;
    private String saveFilePath;


}
