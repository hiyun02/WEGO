package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelDTO {

    private String sendUrl; // 보낼 주소
    private String areaCode; // 지역코드
    private String sigunguCode; // 시구군 코드
    private String contentId; // 컨텐츠 아이디
    private String contentTypeId; // 관광타입 아이디
    private String defaultYN; // 기본정보조회여부
    private String firstImageYN; // 대표 이미지 조회 여부
    private String areacodeYN; // 대표 이미지 조회 여부
    private String catcodeYN; // 대,중,소분류코드조회여부
    private String addrinfoYN; //주소, 상세주소조회여부
    private String mapinfoYN; // 좌표X, Y 조회여부
    private String overviewYN; // 콘텐츠개요조회여부
    private String cat1; // 대분류코드
    private String cat2; // 중분류코드
    private String cat3; // 소분류코드
    private String keyWord; // 키워드
    private String apiUrl;
    private String numOfRows;
    private String pageNo;
}
