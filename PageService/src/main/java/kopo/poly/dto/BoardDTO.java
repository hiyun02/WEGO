package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {
    private Long boardSeq;
    private String startDay;
    private String endDay;
    private String location;
    private String city;
    private String regId;
    private String regDt;

    private String title;
    private String grade;
    private String place;
    private String hashtag;
    private String saveFilePath;
    private String userName;
}
