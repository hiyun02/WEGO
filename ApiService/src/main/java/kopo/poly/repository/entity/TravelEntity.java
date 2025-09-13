package kopo.poly.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Table(name = "TRAVEL")
@Entity
public class TravelEntity {

    @Id
    @Column(name = "TRAVEL_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelSeq;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "STAR_TDAY")
    private String startDay;

    @Column(name = "END_DAY")
    private String endDay;

    @Column(name = "PEOPLE_CNT")
    private String pCnt;

    @Column(name = "REG_ID")
    private String regId;

    @Column(name = "REG_DT")
    private String regDt;
}
