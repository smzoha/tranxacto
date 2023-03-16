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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Shamah M Zoha
 * @since 10-Mar-23
 */

@Service
public class SupportingDocumentService {

    @Autowired
    private SupportingDocumentRepository supportingDocumentRepository;

    public List<SupportingDocumentDto> getFileList() {
        return supportingDocumentRepository.findAll().stream()
                .map(SupportingDocumentService::getSupportingDocumentDto)
                .collect(Collectors.toList());
    }

    public Optional<SupportingDocument> getFile(long id) {
        return supportingDocumentRepository.findById(id);
    }

    public SupportingDocumentDto getFileInfo(long id) {
        Optional<SupportingDocument> file = getFile(id);

        return file.map(SupportingDocumentService::getSupportingDocumentDto).orElse(null);
    }

    @Transactional
    public SupportingDocumentDto uploadFile(MultipartFile file) throws IOException {
        SupportingDocument doc = new SupportingDocument(file.getOriginalFilename(), file.getSize(),
                file.getContentType(), file.getBytes());

        doc = supportingDocumentRepository.save(doc);

        return getSupportingDocumentDto(doc);
    }

    @Transactional
    public void removeFile(SupportingDocument document) {
        supportingDocumentRepository.delete(document);
    }

    private static SupportingDocumentDto getSupportingDocumentDto(SupportingDocument doc) {
        return ResponseUtils.getSupportingDocumentResponse(doc.getId(), doc.getName(), doc.getSize(), doc.getUploadDate());
    }
}
