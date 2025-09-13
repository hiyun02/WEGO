package kopo.poly.repository.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Table(name = "BOARD_POST")
@Entity
public class BoardPostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_POST_SEQ")
    private Long boardPostSeq;

    @NonNull
    @Column(name = "BOARD_SEQ")
    private Long boardSeq;

    @NonNull
    @Column(name = "TITLE", length = 100, nullable = false)
    private String title;

    @NonNull
    @Column(name = "CONTENT", length = 1000, nullable = false)
    private String content;

    @NonNull
    @Column(name = "GRADE", length = 10, nullable = false)
    private String grade;

    @NonNull
    @Column(name = "PLACE", length = 100, nullable = false)
    private String place;

    @NonNull
    @Column(name = "HASHTAG", length = 100, nullable = false)
    private String hashtag;

    /*조회 시 BOARD_SEQ를 가져오려면 현재 DB구조에선 USER_ID가 필요하다고 생각*/
    @NonNull
    @Column(name = "REG_ID", length = 30, nullable = false)
    private String regId;

    /*파일 엔티티 내용*/
    @NonNull
    @Column(name = "SAVE_FILE_NAME", length = 50, nullable = false)
    private String saveFileName;

    @NonNull
    @Column(name = "SAVE_FILE_PATH", length = 200, nullable = false)
    private String saveFilePath;

}
