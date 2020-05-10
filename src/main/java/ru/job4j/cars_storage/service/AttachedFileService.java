package ru.job4j.cars_storage.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars_storage.domain.Advert;
import ru.job4j.cars_storage.domain.AttachedFile;
import ru.job4j.cars_storage.repository.AttachedFileRepository;
import ru.job4j.cars_storage.service.util.RandomUtil;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static java.nio.file.Files.createDirectories;
import static java.nio.file.Paths.get;
import static java.util.Objects.requireNonNull;

@Service
public class AttachedFileService {
    private final Logger log = LoggerFactory.getLogger(AttachedFileService.class);

    private final Environment environment;
    private final AttachedFileRepository attachedFileRepository;

    public AttachedFileService(Environment environment, AttachedFileRepository attachedFileRepository) {
        this.environment = environment;
        this.attachedFileRepository = attachedFileRepository;
    }

    /**
     * Метод для обработки файла. Сохранения на диск и удаления
     * @param files файлы для сохранения
     * @param deleteFileList файлы для удаления
     * @param advertId id объявления для которого обрабатываются файлы
     */
    public void processFile(List<MultipartFile> files, List<Long> deleteFileList, Long advertId) {
        if (files != null && files.size() > 0) {
            this.saveFiles(files, advertId);
        }
        if (deleteFileList!= null && deleteFileList.size() > 0) {
            this.deleteFilesById(deleteFileList);
        }
    }

    public void deleteFiles(List<AttachedFile> attachedFiles) {
        if (attachedFiles != null && !attachedFiles.isEmpty()) {
            attachedFileRepository.deleteAll(attachedFiles);
            for (AttachedFile attachedFile : attachedFiles) {
                File file = new File(attachedFile.getPathToFile());
                if (file.delete()) {
                    log.debug("Файл {} удален", attachedFile.getRealName());
                } else {
                    log.debug("Файл {} не удален", attachedFile.getRealName());
                }
            }
        }
    }

    /**
     * удаление файла по id  и из реопзитория
     *
     * @param deletedId список id  файлов, которые необходимо удалить
     */
    private void deleteFilesById(List<Long> deletedId) {
        List<AttachedFile> attachedFiles = attachedFileRepository.findAllById(deletedId);
        this.deleteFiles(attachedFiles);
    }

    /**
     * Сохранение файлов на диск
     * @param files
     * @param advertId
     */
    private void saveFiles(List<MultipartFile> files, Long advertId) {
        List<AttachedFile> attachedFiles = new LinkedList<>();
        for (MultipartFile file : files) {
            AttachedFile attachedFile = this.createAttachedFileInstance(file, advertId);
            if (attachedFile!= null) {
                attachedFiles.add(attachedFile);
            }
        }
        this.attachedFileRepository.saveAll(attachedFiles);
    }

    private AttachedFile createAttachedFileInstance(MultipartFile file, Long advertId) {
        String name = RandomUtil.generateTokenData() + file.getOriginalFilename();
        try {
            Path dir = createDirectories(get(requireNonNull(environment.getProperty("file-path")), getCurrentDate(), advertId.toString()));
            Files.copy(file.getInputStream(), dir.resolve(name));
            AttachedFile attachedFile = new AttachedFile();
            attachedFile.setRealName(file.getOriginalFilename());
            attachedFile.setFileSize(String.valueOf(file.getSize()));
            attachedFile.setFileExtension(file.getContentType());
            attachedFile.setAdvert(new Advert(advertId));
            attachedFile.setPathToFile(dir.toString() + "/" + name);
            return  attachedFile;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }


    public Resource loadFile(Long id) {
        AttachedFile attachedFile = this.attachedFileRepository.findById(id).orElse(new AttachedFile());
        if (attachedFile.getId()!= null) {
            try {
                Path file = Paths.get(attachedFile.getPathToFile());
                Resource resource = new UrlResource(file.toUri());
                if (resource.exists() || resource.isReadable()) {
                    return resource;
                } else {
                    throw new RuntimeException("FAIL!");
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException("FAIL!");
            }
        }
        return null;
    }


    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(new Date());
    }

}
