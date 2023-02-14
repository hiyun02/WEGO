package kopo.poly.repository.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter // Entity에 저장된 값을 가져오기 위해서 사용
@NoArgsConstructor // 파라미터가 존재하지 않는 생성자 생성 , 현재 파일 NoticeEntity(); 실행할 떄 메모리에 올리는 함수
@AllArgsConstructor // Entity 안의 모든 변수를 기반으로 생성자 생성
@Table(name = "NOTICE") // 연결할 데이터베이스의 테이블명
@DynamicInsert // 데이터를 저장할 떄 Entity 변수의 값이 null인 변수는 제외하고, insert 쿼리 생성하여 저장하기
@DynamicUpdate // 데이터를 수정할 떄 Entity 변수의 값이 null인 변수는 제외하고, update 쿼리 저장하기 ,
// 반드시 Entity 생성할 떄 추가해야됨!!
@Builder // Entity 변수에 값을 저장하기 위한 빌더 생성 ,    데이터를 저장 수정할 떄 사용 , 생성자를 사용하는 것보다 직관적
@Entity // 자바 클래스 Entity인 것을 알려줌

public class NoticeEntity {

    @Id// 테이블의 PK역할인 컬럼에 적용
    @GeneratedValue(strategy = GenerationType.IDENTITY)// pk 자동증가
    @Column(name = "notice_seq") // 테이블 컬럼과 매칭
    private Long noticeSeq;

    @NonNull
    @Column(name = "title", length = 500, nullable = false)
    private String title;

    @NonNull
    @Column(name = "contents", nullable = false)
    private String contents;

    @NonNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "read_cnt", nullable = false)
    private Long readCnt;

    @Column(name = "reg_id", updatable = false) // updatable : update 쿼리가 실행될 떄 값을 수정하지 않는다
    private String regId;

    @Column(name = "reg_dt", updatable = false)
    private String regDt;

    @Column(name = "chg_id")
    private String chgId;

    @Column(name = "chg_dt")
    private String chgDt;
}
