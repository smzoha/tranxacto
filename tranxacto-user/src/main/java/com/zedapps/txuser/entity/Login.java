package com.zedapps.txuser.entity;

import com.zedapps.common.dto.LoginRequestDto;
import com.zedapps.txuser.entity.enums.Role;
import com.zedapps.txuser.entity.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Shamah M Zoha
 * @since 19-Apr-23
 */

@Entity
@Table(name = "login")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Login implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "seq_login", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seq_login", sequenceName = "seq_login", allocationSize = 1)
    private long id;

    @NotBlank
    @Size(max = 64)
    @Column(length = 64, nullable = false, updatable = false, unique = true)
    private String username;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 64, nullable = false)
    private Status status;

    @NotBlank
    @Email
    @Size(max = 128)
    @Column(length = 128, nullable = false)
    private String email;

    @NotBlank
    @Column(length = 64, nullable = false)
    private String firstName;

    @NotBlank
    @Column(length = 64, nullable = false)
    private String lastName;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "role")
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public Login(LoginRequestDto loginRequestDto) {
        this.username = loginRequestDto.getUsername();
        this.email = loginRequestDto.getEmail();
        this.firstName = loginRequestDto.getFirstName();
        this.lastName = loginRequestDto.getLastName();

        this.status = Status.ACTIVE;

        this.roles = loginRequestDto.getRoles().stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }
}
