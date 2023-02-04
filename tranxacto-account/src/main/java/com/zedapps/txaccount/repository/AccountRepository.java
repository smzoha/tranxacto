package com.zedapps.txaccount.repository;

import com.zedapps.txaccount.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Shamah M Zoha
 * @since 27-Jan-23
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "FROM Account WHERE status = 'ACTIVE'")
    List<Account> findAllActiveAccounts();
}
