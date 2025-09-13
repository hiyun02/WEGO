package kopo.poly.repository;

import kopo.poly.repository.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    BoardEntity findByBoardSeq(Long boardSeq);

    List<BoardEntity> findByHashtag(String hashtag);

    Page<BoardEntity> findAll(Pageable pageable);

}

