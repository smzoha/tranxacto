package com.zedapps.txdocs.repository;

import com.zedapps.txdocs.entity.SupportingDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Shamah M Zoha
 * @since 10-Mar-23
 */

@Repository
public interface SupportingDocumentRepository extends JpaRepository<SupportingDocument, Long> {
}
