package kopo.poly.repository;

import kopo.poly.repository.entity.FileInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfoEntity, Long> {
}
