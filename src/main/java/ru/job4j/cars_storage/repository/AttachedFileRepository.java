package ru.job4j.cars_storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.cars_storage.domain.AttachedFile;

import java.util.List;

public interface AttachedFileRepository extends JpaRepository<AttachedFile, Long> {
    List<AttachedFile> findAllByAdvert_Id(Long id);
}
