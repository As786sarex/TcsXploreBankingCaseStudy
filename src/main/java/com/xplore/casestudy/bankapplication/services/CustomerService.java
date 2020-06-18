package com.xplore.casestudy.bankapplication.services;

import com.xplore.casestudy.bankapplication.models.Customer;
import com.xplore.casestudy.bankapplication.models.CustomerStatus;
import com.xplore.casestudy.bankapplication.repositories.CustomerRepository;
import com.xplore.casestudy.bankapplication.repositories.CustomerStatusRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerStatusRepository customerStatusRepository;

    public CustomerService(CustomerRepository customerRepository, CustomerStatusRepository customerStatusRepository) {
        this.customerRepository = customerRepository;
        this.customerStatusRepository = customerStatusRepository;
    }

    public Customer saveCustomer(Customer customer) {
        final Customer save = customerRepository.save(customer);
        final CustomerStatus status = new CustomerStatus(save.getSsn(), save.getCustomerId(),
                "Active", "Account Created Successfully!", LocalDateTime.now());
        customerStatusRepository.save(status);
        return save;
    }

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(String id) {
        return this.customerRepository.getCustomerByCustomerId(Long.parseLong(id));
    }

    public void updateCustomer(Customer customer) {
        this.customerRepository.updateCustomerById(customer.getCustomerId(),
                customer.getName(), customer.getAddress(), customer.getAge());
    }

    public List<CustomerStatus> getAllCustomerStatus() {
        return this.customerStatusRepository.findAll();
    }

    public void deleteCustomerById(Customer customer) {
        this.customerRepository.deleteById(customer.getCustomerId());
        this.customerStatusRepository.deleteById(customer.getCustomerId());
    }


}
