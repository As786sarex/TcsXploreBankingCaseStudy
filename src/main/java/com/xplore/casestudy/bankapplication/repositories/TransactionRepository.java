package com.xplore.casestudy.bankapplication.repositories;

import com.xplore.casestudy.bankapplication.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> getTop10BySourceAccountIdOrderByTransactionDateDesc(Long id);
    @Transactional
    @Query("select t from Transaction t where t.transactionDate >= :start and t.transactionDate <= :end order by t.transactionDate desc")
    List<Transaction> getAllBetweenStartAndEnd(@Param("start") LocalDate a,@Param("end") LocalDate b);
}
