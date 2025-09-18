package kopo.poly.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Table(name = "TRAVEL_CHOICE")
@Entity
public class TravelChoiceEntity {

    @Id
    @Column(name = "TRAVEL_CHOICE_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelChoiceSeq;

    @Column(name = "TRAVEL_SEQ")
    private Long travelSeq;

    @Column(name = "TRAVEL_TITLE")
    private String travelTitle;

    @Column(name = "addr1")
    private String addr1;

    @Column(name = "MAP_X")
    private String mapX;

    @Column(name = "MAP_Y")
    private String mapY;

    @Column(name = "FIRST_IMAGE")
    private String firstImage;

    @Column(name = "FIRST_IMAGE2")
    private String firstImage2;

    @Column(name = "REG_ID")
    private String regId;

    @Column(name = "REG_DT")
    private String regDt;


}
