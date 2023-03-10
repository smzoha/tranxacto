package com.zedapps.txdocs.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.io.Serializable;
import java.sql.Types;
import java.util.Date;

/**
 * @author Shamah M Zoha
 * @since 10-Mar-23
 */

@Entity
@Table(name = "supporting_document")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupportingDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_supporting_document")
    @SequenceGenerator(name = "seq_supporting_document", sequenceName = "seq_supporting_document", allocationSize = 1)
    private long id;

    @NotBlank
    @Size(max = 128)
    @Column(length = 128, nullable = false)
    private String name;

    @NotNull
    @Min(value = 0L)
    @Column(nullable = false)
    private Long size;

    @NotNull
    @Column(nullable = false)
    private String type;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date uploadDate;

    @Lob
    @JdbcTypeCode(Types.BINARY)
    @NotNull
    private byte[] data;

    public SupportingDocument(String name, long size, String type, byte[] data) {
        this.name = name;
        this.size = size;
        this.type = type;
        this.data = data;
    }

    @PrePersist
    public void onPrePersist() {
        this.setUploadDate(new Date());
    }
}
