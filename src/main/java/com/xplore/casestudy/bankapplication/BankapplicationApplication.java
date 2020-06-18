package com.xplore.casestudy.bankapplication;

import com.xplore.casestudy.bankapplication.models.User;
import com.xplore.casestudy.bankapplication.repositories.CustomerRepository;
import com.xplore.casestudy.bankapplication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.xplore.casestudy.bankapplication.config.AuthConfiguration.*;

@SpringBootApplication
public class BankapplicationApplication implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final UserRepository repository;
    @Autowired
    private final PasswordEncoder encoder;

    public BankapplicationApplication(UserRepository repository, PasswordEncoder encoder, CustomerRepository customerRepository) {
        this.repository = repository;
        this.encoder = encoder;
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BankapplicationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User executive = new User(1, "asif", encoder.encode("k3333"), ROLE_EXECUTIVE, System.currentTimeMillis());
        User admin = new User(2, "admin", encoder.encode("admin"), ROLE_ADMIN, System.currentTimeMillis());
        User cash = new User(3, "cash", encoder.encode("k3333"), ROLE_CASHIER, System.currentTimeMillis());
/*
        Customer customer1 = new Customer(100000000, null, "asif", "Mumbai", 21);
        Customer customer2 = new Customer(100000001, null, "asif", "Mumbai", 21);
        Customer customer3 = new Customer(100000002, null, "asif", "Mumbai", 21);
        Customer customer4 = new Customer(100000003, null, "asif", "Mumbai", 21);
        customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3, customer4));
        repository.saveAll(Arrays.asList(executive, admin, cash));*/
    }
}
