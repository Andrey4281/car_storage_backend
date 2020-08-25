package ru.job4j.cars_storage.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars_storage.domain.Advert;
import ru.job4j.cars_storage.service.AdvertService;

import javax.servlet.annotation.MultipartConfig;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@MultipartConfig
@CrossOrigin("*")
public class AdvertController {
    private final Logger log = LoggerFactory.getLogger(AdvertController.class);

    private final AdvertService advertService;

    public AdvertController(AdvertService advertService) {
        this.advertService = advertService;
    }

    /**
     * Create new advert
     * @param advert
     * @param files
     * @return
     */
    @PostMapping(value = "/adverts/new",  consumes = { "multipart/form-data" })
    public ResponseEntity<Advert> createAdvert(@RequestPart("advert") Advert advert,
                                               @RequestPart(value = "files", required = false) List<MultipartFile> files) {

        log.debug("REST request to save Advert : {}", advert);
        advertService.save(advert, files, null);

        return new ResponseEntity<>(advert, HttpStatus.OK);
    }

    /**
     * Update existed advert
     * @param advert
     * @param files
     * @param deleteFileList
     * @return
     * @throws BadRequestException
     */
    @PutMapping(value = "/adverts/edit",  consumes = { "multipart/form-data" })
    public ResponseEntity<Advert> updateAdvert(@RequestPart("advert") Advert advert,
                                               @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                               @RequestParam(value ="deleteFileList", required = false) List<Long> deleteFileList) throws BadRequestException {

        if (advert.getId() == null) {
            throw new BadRequestException(String.format("Advert does not exists %d", advert.getId()));
        }
        log.debug("REST request to update Advert : {}", advert);
        advertService.save(advert, files, deleteFileList);

        return new ResponseEntity<>(advert, HttpStatus.OK);
    }

    @GetMapping("/adverts")
    public ResponseEntity<List<Advert>> getAllAdverts(@RequestParam Map<String, String> reqParam) {
        log.debug("REST request to get all adverts");
        Page<Advert> page = advertService.findAll(reqParam);
        HttpHeaders headers = new HttpHeaders();
        headers.set("totalSize", String.valueOf(page.getTotalElements()));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/adverts/{id}")
    public ResponseEntity<?> getAdvert(@PathVariable Long id) {
        Optional<Advert> advert = advertService.findOne(id);

        if (!advert.isPresent()) {
            return new ResponseEntity<>("Advert not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(advert, HttpStatus.OK);
        }
    }

    @DeleteMapping("/adverts/delete/{id}")
    public ResponseEntity<?> deleteAdvert(@PathVariable Long id) {
        Optional<Advert> advert = advertService.findOne(id);
        if (!advert.isPresent()) {
            return new ResponseEntity<>("Advert not found", HttpStatus.NOT_FOUND);
        } else {
            advertService.delete(id);
            return new ResponseEntity<>(advert, HttpStatus.OK);
        }
    }

}
