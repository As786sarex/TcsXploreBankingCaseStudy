package com.xplore.casestudy.bankapplication.repositories;

import com.xplore.casestudy.bankapplication.models.CustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerStatusRepository extends JpaRepository<CustomerStatus, Long> {
}
