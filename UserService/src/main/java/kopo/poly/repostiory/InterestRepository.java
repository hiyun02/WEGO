package kopo.poly.repostiory;

import kopo.poly.repostiory.entity.InterestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestRepository extends JpaRepository<InterestEntity, Long> {

    List<InterestEntity> findAllByRegId(String userId);
}
