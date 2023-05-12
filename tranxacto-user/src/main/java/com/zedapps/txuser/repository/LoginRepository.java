package com.zedapps.txuser.repository;

import com.zedapps.txuser.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Shamah M Zoha
 * @since 01-May-23
 */

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {

    @Query("FROM Login WHERE status = 'ACTIVE'")
    List<Login> getActiveLogins();

    @Query("FROM Login WHERE username = :username")
    Optional<Login> findByUsername(String username);

}
