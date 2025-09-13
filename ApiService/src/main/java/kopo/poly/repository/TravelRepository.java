package kopo.poly.repository;

import kopo.poly.repository.entity.TravelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelRepository extends JpaRepository<TravelEntity, Long> {

    TravelEntity findByTitle(String title);

    List<TravelEntity> findAllByRegId(String RegId);

}
