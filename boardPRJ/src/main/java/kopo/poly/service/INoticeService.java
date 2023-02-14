package kopo.poly.service;


import kopo.poly.dto.NoticeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface INoticeService {

    /**
     *
     * 공지사항 가져오기
     */
    Page<NoticeDTO> getNoticeList(Pageable pageable) throws Exception;

    /**
     * 공지사항 상세 정보가져오기
     * pDTO 상세정보를 반환하는 정보, type은 조회수 증가여부( 수정에서는 증가가 필요없기 때문에)
     */
    NoticeDTO getNoticeInfo(NoticeDTO pDTO, boolean type) throws Exception;

    /**
     * 해당 공지사항 수정하기
     * pDTO : 공지사항을 수정하기 위한 정보
     */
    void updateNoticeInfo(NoticeDTO pDTO) throws Exception;


    /**
     * 해당 공지사항 삭제하기
     * pDTO는 공지사항을 삭제하기 위한 정보
     */
    void deleteNoticeInfo(NoticeDTO pDTO) throws Exception;


    /**
     * 해당 공지사항 저장하기
     * pDTO 공지사항 저장하기위한 정보
     */
    void insertNoticeInfo(NoticeDTO pDTO) throws Exception;


}
