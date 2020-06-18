package com.xplore.casestudy.bankapplication.repositories;

import com.xplore.casestudy.bankapplication.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> getCustomerByCustomerId(Long id);

    @Modifying
    @Transactional
    @Query("update Customer c set c.name=?2,c.address=?3,c.age=?4 where c.customerId=?1")
    void updateCustomerById(Long custId, String name, String address, int age);
}
