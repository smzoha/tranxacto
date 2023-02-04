package com.zedapps.txaccount.entity;

import com.zedapps.txaccount.entity.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Shamah M Zoha
 * @since 27-Jan-23
 */

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_account")
    @SequenceGenerator(name = "seq_account", sequenceName = "seq_account", allocationSize = 1)
    private long id;

    @NotBlank
    @Size(max = 128)
    @Column(length = 128, nullable = false)
    private String name;

    @NotNull
    @ManyToOne(optional = false)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "type")
    private AccountType type;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String accountNumber;

    @Size(max = 1000)
    @Column(length = 1000)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 16, nullable = false)
    private Status status;

    @Min(value = 0L)
    private double openingBalance;

    private String organization;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @PrePersist
    public void onPrePersist() {
        this.setCreatedDate(new Date());
        this.setUpdatedDate(new Date());
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setUpdatedDate(new Date());
    }
}
