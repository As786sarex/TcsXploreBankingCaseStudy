package com.xplore.casestudy.bankapplication.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "account_status")
@Table(name = "account_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AccountStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "my_seq_gen")
    @Column(name = "ws_acct_id")
    @SequenceGenerator(name = "my_seq_gen", initialValue = 100000000)
    private Long accountId;
    @Column(name = "ws_cust_id")
    private long customerId;
    private String accountType;
    private String accountStatus;
    private String message;
    private LocalDateTime lashUpdateTime;
}
