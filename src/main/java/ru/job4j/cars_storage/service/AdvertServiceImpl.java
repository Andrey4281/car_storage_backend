package ru.job4j.cars_storage.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars_storage.domain.Advert;
import ru.job4j.cars_storage.domain.AttachedFile;
import ru.job4j.cars_storage.repository.AdvertRepository;
import ru.job4j.cars_storage.repository.FileRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdvertServiceImpl implements AdvertService {
    private final AdvertRepository advertRepository;
    private final FileRepository fileRepository;
    private final FileService fileService;

    public AdvertServiceImpl(AdvertRepository advertRepository, FileRepository fileRepository, FileService fileService) {
        this.advertRepository = advertRepository;
        this.fileRepository = fileRepository;
        this.fileService = fileService;
    }

    @Override
    public void delete(Long id) {
        advertRepository.deleteById(id);
        List<AttachedFile> attachedFiles = fileRepository.findAllByAdvert_Id(id);
        fileService.deleteFiles(attachedFiles);
    }

    @Override
    public Optional<Advert> findOne(Long id) {
        return advertRepository.findById(id);
    }

    /**
     * Метод пока в разработке, сделать пагинацию и критерию
     * @return
     */
    @Override
    public List<Advert> findAll() {
        return advertRepository.findAll();
    }

    @Override
    public Advert save(Advert advert, List<MultipartFile> files, List<Long> deleteFiles) {
        Advert advertSaved = advertRepository.save(advert);
        this.fileService.processFile(files, deleteFiles, advertSaved.getId());
        return advertSaved;
    }
}
