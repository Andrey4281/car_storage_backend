package ru.job4j.cars_storage.web;


import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cars_storage.service.AttachedFileService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AttachedFileController {
    private final AttachedFileService attachedFileService;

    public AttachedFileController(AttachedFileService attachedFileService) {
        this.attachedFileService = attachedFileService;
    }

    @GetMapping("/attached-files/download/{id}")
    public ResponseEntity<Resource> exportTranscriptions(@PathVariable Long id, HttpServletResponse response) {
        Resource file = this.attachedFileService.loadFile(id);
        return ResponseEntity.ok().body(file);
    }
}
