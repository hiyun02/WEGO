package kopo.poly.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board_PostDTO{
    private Long boardPostSeq;
    private Long boardSeq;
    private String title;
    private String content;
    private String grade;
    private String place;
    private String hashtag;
    private String regId;
    private String saveFilePath;
    private String saveFileName;
}
