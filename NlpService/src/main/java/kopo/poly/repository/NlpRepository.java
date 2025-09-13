package kopo.poly.repository;

import kopo.poly.repository.entity.NlpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NlpRepository extends JpaRepository<NlpEntity, Long> {

    List<NlpEntity> findAllByLocationAndCity(String location, String city);
}
