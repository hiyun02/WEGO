package kopo.poly.repostiory.entity;

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
@Table(name = "USER_INFO")
@Entity
public class UserEntity {

    @Id
    @Column(name = "USER_ID", length = 30, nullable = false)
    private String userId;

    @NonNull
    @Column(name = "PASSWORD", length = 64, nullable = false)
    private String password;

    @NonNull
    @Column(name = "USER_NAME", length = 30, nullable = false)
    private String userName;

    @NonNull
    @Column(name = "USER_NICKNAME", length = 30, nullable = false)
    private String userNickName;

    @NonNull
    @Column(name = "EMAIL", length = 128, nullable = false)
    private String email;

    @NonNull
    @Column(name = "USER_AGE", length = 30, nullable = false)
    private String userAge;

    @NonNull
    @Column(name = "GENDER", length = 3, nullable = false)
    private String gender;

    @NonNull
    @Column(name = "INTRODUCE", length = 100, nullable = false)
    private String introDuce;

    @NonNull
    @Column(name = "ROLES", length = 30, nullable = false)
    private String roles;

    @Column(name = "FILE_PATH", length = 200, nullable = false)
    private String filePath;
}
