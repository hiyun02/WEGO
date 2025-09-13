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
@Table(name = "FILE")
@Entity
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_SEQ")
    private Long fileSeq;

    @NonNull
    @Column(name = "REG_ID", length = 30, nullable = false)
    private String regId;

    @NonNull
    @Column(name = "REG_DT", length = 30, nullable = false)
    private String regDt;
}
