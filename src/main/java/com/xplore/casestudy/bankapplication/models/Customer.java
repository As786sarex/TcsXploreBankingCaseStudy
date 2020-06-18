package com.xplore.casestudy.bankapplication.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Customer {
    @Min(value = 100000000L)
    @Column(name = "ws_ssn", unique = true)
    @NotBlank
    private long ssn;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "my_seq_gen")
    @Column(name = "ws_cust_id")
    @Min(value = 100000000L)
    @NotBlank
    @SequenceGenerator(name = "my_seq_gen", initialValue = 100000000)
    private Long customerId;
    @Min(value = 100000000L)
    @Column(name = "ws_name", updatable = true)
    @NotBlank
    private String name;
    @Column(name = "ws_adrs", updatable = true)
    @NotBlank
    private String address;
    @Column(name = "ws_age", updatable = true)
    @NotBlank
    private int age;
}
