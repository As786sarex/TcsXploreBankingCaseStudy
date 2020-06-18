package com.xplore.casestudy.bankapplication.models;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity(name = "customer_status")
@Table(name = "customer_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerStatus {
    @Min(value = 100000000L)
    @Column(name = "ws_ssn", unique = true)
    @NotBlank
    private long ssn;
    @Id
    @Column(name = "ws_cust_id")
    @Min(value = 100000000L)
    @NotBlank
    private Long customerId;

    private String status;

    private String message;

    private LocalDateTime lastUpdated;

}
