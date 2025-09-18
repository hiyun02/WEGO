package kopo.poly.repostiory;

import kopo.poly.repostiory.entity.UserEntity;
import lombok.experimental.PackagePrivate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUserId(String userId);

    Optional<UserEntity> findByEmail(String email);

    UserEntity findByUserNameAndEmail(String userName, String email);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE USER_INFO SET PASSWORD =:password WHERE USER_ID =:userId", nativeQuery = true)
    int updateUserPassword(@Param("password") String password, @Param("userId") String userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE USER_INFO SET FILE_PATH =:filePath,USER_NAME =:userName, USER_NICKNAME =:userNickName, USER_AGE =:userAge, GENDER =:gender, EMAIL =:email, INTRODUCE =:introDuce WHERE USER_ID =:userId", nativeQuery = true)
    int updateUserInfo(@Param("filePath") String filePath, @Param("userName") String userName, @Param("userNickName") String userNickName, @Param("userAge") String userAge, @Param("gender") String gender, @Param("email") String email, @Param("introDuce") String introDuce,@Param("userId") String userId);
}
