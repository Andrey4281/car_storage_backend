package ru.job4j.cars_storage.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars_storage.domain.Advert;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AdvertService {
    void delete(Long id);
    Optional<Advert> findOne(Long id);
    Page<Advert> findAll(Map<String, String> reqParam);
    Advert save(Advert advert, List<MultipartFile> files, List<Long> deleteFiles);
}
