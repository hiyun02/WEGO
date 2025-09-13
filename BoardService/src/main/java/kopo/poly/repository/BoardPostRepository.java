package kopo.poly.repository;

import kopo.poly.dto.NlpDataDTO;
import kopo.poly.repository.entity.BoardEntity;
import kopo.poly.repository.entity.BoardPostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BoardPostRepository extends JpaRepository<BoardPostEntity, Long> {
    List<BoardPostEntity> findByBoardSeq(Long boardSeq);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM BOARD_POST WHERE BOARD_SEQ = :boardSeq", nativeQuery = true)
    int deleteByBoardSeq(@Param("boardSeq") Long boardSeq);


    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT B.LOCATION, B.CITY, P.PLACE, P.CONTENT, P.SAVE_FILE_PATH" +
            "FROM BOARD B, BOARD_POST P" +
            "WHERE B.BOARD_SEQ=P.BOARD_SEQ", nativeQuery = true)
    List<NlpDataDTO> getNlpDataList();
}
