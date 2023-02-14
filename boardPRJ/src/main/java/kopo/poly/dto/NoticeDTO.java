package kopo.poly.dto;

import kopo.poly.repository.entity.NoticeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDTO {
    private Long noticeSeq; // 기본키, 순번
    private String title; // 제목
    private String contents; // 글 내용
    private String userId; // 작성자
    private String readCnt; // 조회수
    private String regId; // 등록자 아이디
    private String regDt; // 등록일
    private String chgId; // 수정자 아이디
    private String chgDt; // 수정일

//    private String userName; // 등록자명

    public static NoticeDTO toDTO(final NoticeEntity entity) {
        NoticeDTO dto = new NoticeDTO();
        dto.setNoticeSeq(entity.getNoticeSeq());
        dto.setTitle(entity.getTitle());
        dto.setContents(entity.getContents());
        dto.setUserId(entity.getUserId());
        dto.setReadCnt(entity.getReadCnt().toString());
        dto.setRegId(entity.getRegId());
        dto.setRegDt(entity.getRegDt());
        dto.setChgId(entity.getChgId());
        dto.setChgDt(entity.getChgDt());
        return dto;
    }


}
