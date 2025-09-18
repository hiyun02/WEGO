package kopo.poly.repostiory.entity;

import io.micrometer.core.lang.Nullable;
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
@Table(name = "INTEREST")
@Entity
public class InterestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_INTEREST_SEQ")
    private Long interestSeq;

    @Nullable
    @Column(name = "CONTENT", length = 10, nullable = false)
    private String interestContent;

    @Column(name = "REG_ID", length = 30, nullable = false)
    private String regId;

    @Column(name = "REG_DT", length = 30, nullable = false)
    private String regDT;
}
