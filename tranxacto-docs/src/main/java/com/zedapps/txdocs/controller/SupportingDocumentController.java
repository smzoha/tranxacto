package com.zedapps.txdocs.controller;

import com.zedapps.common.dto.SupportingDocumentDto;
import com.zedapps.txdocs.entity.SupportingDocument;
import com.zedapps.txdocs.service.SupportingDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author Shamah M Zoha
 * @since 10-Mar-23
 */

@RestController
public class SupportingDocumentController {

    @Autowired
    private SupportingDocumentService supportingDocumentService;

    @PostMapping("/upload")
    public SupportingDocumentDto upload(@RequestParam MultipartFile file) {
        try {
            return supportingDocumentService.uploadFile(file);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File Upload Failed");
        }
    }

    @GetMapping("/all")
    public List<SupportingDocumentDto> getDocuments() {
        return supportingDocumentService.getFileList();
    }

    @GetMapping("/info/{id}")
    public SupportingDocumentDto getDocumentInfo(@PathVariable long id) {
        SupportingDocumentDto supportingDocumentDto = supportingDocumentService.getFileInfo(id);

        if (supportingDocumentDto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found by id!");
        }

        return supportingDocumentDto;
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable long id) {
        SupportingDocument supportingDocument = getSupportingDocument(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(supportingDocument.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + supportingDocument.getName())
                .body(supportingDocument.getData());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> removeFile(@PathVariable long id) {
        supportingDocumentService.removeFile(getSupportingDocument(id));

        return ResponseEntity.ok("Successfully deleted!");
    }

    private SupportingDocument getSupportingDocument(long id) {
        Optional<SupportingDocument> document = supportingDocumentService.getFile(id);

        if (document.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found by id!");
        }

        return document.get();
    }
}
