package com.zedapps.txaccount.repository;

import com.zedapps.txaccount.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Shamah M Zoha
 * @since 27-Jan-23
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
