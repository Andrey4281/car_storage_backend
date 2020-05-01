package ru.job4j.cars_storage.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars_storage.domain.Advert;
import ru.job4j.cars_storage.domain.AttachedFile;
import ru.job4j.cars_storage.domain.User;
import ru.job4j.cars_storage.repository.AdvertRepository;
import ru.job4j.cars_storage.repository.AttachedFileRepository;
import ru.job4j.cars_storage.repository.UserRepository;

import java.sql.Timestamp;
import java.util.*;

@Service
public class AdvertServiceImpl implements AdvertService {
    private final Logger log = LoggerFactory.getLogger(AdvertServiceImpl.class);
    private final AdvertRepository advertRepository;
    private final AttachedFileRepository attachedFileRepository;
    private final UserRepository userRepository;
    private final AttachedFileService attachedFileService;
    private final FilterService filterService;

    public AdvertServiceImpl(AdvertRepository advertRepository, AttachedFileRepository attachedFileRepository, UserRepository userRepository, AttachedFileService attachedFileService, FilterService filterService) {
        this.advertRepository = advertRepository;
        this.attachedFileRepository = attachedFileRepository;
        this.userRepository = userRepository;
        this.attachedFileService = attachedFileService;
        this.filterService = filterService;
    }

    @Override
    public void delete(Long id) {
        advertRepository.deleteById(id);
        List<AttachedFile> attachedFiles = attachedFileRepository.findAllByAdvert_Id(id);
        attachedFileService.deleteFiles(attachedFiles);
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
    public List<Advert> findAll(Map<String, String> reqParam) {
        return advertRepository.findAll(this.buildCriteria(reqParam));
    }

    @Override
    public Advert save(Advert advert, List<MultipartFile> files, List<Long> deleteFiles) {
        if (advert.getId() == null) {
            advert.setCreated(new Timestamp(new Date().getTime()));
            advert.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            log.debug("current user login {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            log.debug("current advert: {}", advert.toString());
        }
        Advert advertSaved = advertRepository.save(advert);
        this.attachedFileService.processFile(files, deleteFiles, advertSaved.getId());
        return advertSaved;
    }

    private Specification<Advert> buildCriteria(Map<String, String> reqParam) {
        Specification<Advert> criteria = null;
        if (reqParam.get("userLogin") != null) {
            criteria = filterService.getUserFilter(reqParam.get("userLogin"));
        }
        if (reqParam.get("categoryCar") != null) {
            criteria = criteria.and(filterService.getCategoryFilter(reqParam.get("categoryCar")));
        }
        if (reqParam.get("countDay") != null) {
            Calendar calendar = new GregorianCalendar();
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.DAY_OF_YEAR, -Integer.parseInt(reqParam.get("countDay")));
            criteria = criteria.and(filterService.getDateFilter(new Timestamp(calendar.getTime().getTime())));
        }
        return criteria;
    }
}
