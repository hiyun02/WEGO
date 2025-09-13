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
@Table(name = "BOARD")
@Entity
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_SEQ")
    private Long boardSeq;

    @NonNull
    @Column(name = "START_DAY", length = 30, nullable = false)
    private String startDay;

    @NonNull
    @Column(name = "END_DAY", length = 30, nullable = false)
    private String endDay;

    @NonNull
    @Column(name = "LOCATION", length = 30, nullable = false)
    private String location;

    @NonNull
    @Column(name = "city", length = 30, nullable = false)
    private String city;

    @NonNull
    @Column(name = "REG_ID", length = 30, nullable = false)
    private String regId;

    @NonNull
    @Column(name = "REG_DT", length = 30, nullable = false)
    private String regDt;

    @NonNull
    @Column(name = "SAVE_FILE_PATH", length = 200, nullable = false)
    private String saveFilePath;

    @NonNull
    @Column(name = "TITLE", length = 100, nullable = false)
    private String title;

    @NonNull
    @Column(name = "GRADE", length = 10, nullable = false)
    private String grade;

    @NonNull
    @Column(name = "PLACE", length = 100, nullable = false)
    private String place;

    @NonNull
    @Column(name = "HASHTAG", length = 100, nullable = false)
    private String hashtag;

    @NonNull
    @Column(name = "USERNAME", length = 100, nullable = false)
    private String userName;


}
