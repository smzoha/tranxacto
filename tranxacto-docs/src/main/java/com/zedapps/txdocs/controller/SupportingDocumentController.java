package com.zedapps.txdocs.controller;

import com.zedapps.txdocs.entity.SupportingDocument;
import com.zedapps.txdocs.service.SupportingDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

/**
 * @author Shamah M Zoha
 * @since 10-Mar-23
 */

@RestController
public class SupportingDocumentController {

    @Autowired
    private SupportingDocumentService supportingDocumentService;

    @PostMapping("/upload")
    private SupportingDocument upload(@RequestParam MultipartFile file) {
        try {
            return supportingDocumentService.uploadFile(file);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File Upload Failed");
        }
    }
}
