package kopo.poly.repository;

import kopo.poly.repository.entity.NoticeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

    Page<NoticeEntity> findAllByOrderByNoticeSeqDesc(Pageable pageable); // NoticeSeq 내림차순으로 전체조회

    NoticeEntity findByNoticeSeq(Long noticeSeq); // notieseq값을 조건절로 사용하여 단건조회


    @Modifying(clearAutomatically = true) // native쿼리를 사용할 떄 영속성이 꺠지니까 entity 내용을 제거
                                            // 값을 가져오는거는 다음번 조회 떄 entity 값 자동추가

    @Query(value = "UPDATE NOTICE A SET A.READ_CNT = IFNULL(A.READ_CNT,0) +1 WHERE A.NOTICE_SEQ = :noticeSeq",
            nativeQuery = true)

    int updateReadCnt(@Param("noticeSeq") Long noticeSeq);

}
