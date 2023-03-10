package com.zedapps.txdocs.service;

import com.zedapps.common.dto.SupportingDocumentDto;
import com.zedapps.common.util.ResponseUtils;
import com.zedapps.txdocs.entity.SupportingDocument;
import com.zedapps.txdocs.repository.SupportingDocumentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Shamah M Zoha
 * @since 10-Mar-23
 */

@Service
public class SupportingDocumentService {

    @Autowired
    private SupportingDocumentRepository supportingDocumentRepository;

    @Transactional
    public SupportingDocumentDto uploadFile(MultipartFile file) throws IOException {
        SupportingDocument doc = new SupportingDocument(file.getOriginalFilename(), file.getSize(), file.getBytes());

        doc = supportingDocumentRepository.save(doc);

        return ResponseUtils.getSupportingDocumentResponse(doc.getId(), doc.getName(), doc.getSize(), doc.getUploadDate());
    }
}
