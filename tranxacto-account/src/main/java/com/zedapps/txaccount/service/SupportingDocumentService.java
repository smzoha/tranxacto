package com.zedapps.txaccount.service;

import com.zedapps.common.dto.SupportingDocumentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Shamah M Zoha
 * @since 18-Mar-23
 */

@FeignClient(value = "supporting-docs-api", path = "/docs")
public interface SupportingDocumentService {

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SupportingDocumentDto uploadDocument(@RequestParam MultipartFile file);

    @GetMapping("/info/{id}")
    public SupportingDocumentDto getDocumentInfo(@PathVariable long id);

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadDocument(@PathVariable long id);

    @DeleteMapping("/delete/{id}")
    public String removeFile(@PathVariable long id);
}
