package com.xplore.casestudy.bankapplication.repositories;

import com.xplore.casestudy.bankapplication.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Transactional
    @Query("select accountId from account")
    List<Long> getAllAccountId();

    @Transactional
    @Modifying
    @Query("update account as a set a.accountBalance=?2,a.lastTransactionDate=?3 where a.accountId=?1")
    void updateMoneyAfterTransaction(Long id, int amount, LocalDate now);

    Optional<Account> getFirstByCustomerId(Long customerId);
}
