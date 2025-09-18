package kopo.poly.repository;

import kopo.poly.repository.entity.TravelChoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelChoiceRepository extends JpaRepository<TravelChoiceEntity, Long> {

    List<TravelChoiceEntity> findAllByTravelSeqAndRegId(Long travelSeq, String regId);
}
