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
@Table(name = "FILE_INFO")
@Entity
public class FileInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_INFO_SEQ")
    private Long fileInfoSeq;

    @NonNull
    @Column(name = "FILE_SEQ")
    private Long fileSeq;

    @NonNull
    @Column(name = "ORG_FILE_NAME", length = 50, nullable = false)
    private String orgFileName;

    @NonNull
    @Column(name = "SAVE_FILE_NAME", length = 50, nullable = false)
    private String saveFileName;

    @NonNull
    @Column(name = "SAVE_FILE_PATH", length = 200, nullable = false)
    private String saveFilePath;

    @NonNull
    @Column(name = "REG_ID", length = 30, nullable = false)
    private String regId;

    @NonNull
    @Column(name = "REG_DT", length = 30, nullable = false)
    private String regDt;

    @NonNull
    @Column(name = "CHG_ID", length = 30, nullable = false)
    private String chgId;

    @NonNull
    @Column(name = "CHG_DT", length = 30, nullable = false)
    private String chgDt;

}
