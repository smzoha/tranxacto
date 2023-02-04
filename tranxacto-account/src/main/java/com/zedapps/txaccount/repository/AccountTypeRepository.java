package com.zedapps.txaccount.repository;

import com.zedapps.txaccount.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Shamah M Zoha
 * @since 28-Jan-23
 */

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
}
