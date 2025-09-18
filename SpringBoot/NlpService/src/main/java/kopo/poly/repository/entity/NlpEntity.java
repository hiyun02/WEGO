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
@Table(name = "NLP")
@Entity
public class NlpEntity {

    @Id
    @Column(name = "NLP_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nlpSeq;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "CITY")
    private String city;

    @Column(name = "PLACE")
    private String place;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "SAVE_FILE_PATH")
    private String saveFilePath;

    @Column(name = "RESULT")
    private String result;

}
