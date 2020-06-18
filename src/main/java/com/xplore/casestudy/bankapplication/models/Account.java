package com.xplore.casestudy.bankapplication.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity(name = "account")
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "my_seq_gen")
    @Column(name = "ws_acct_id")
    @SequenceGenerator(name = "my_seq_gen", initialValue = 100000000)
    private Long accountId;
    @Column(name = "ws_cust_id")
    private long customerId;
    @Column(name = "ws_acct_type")
    private String accountType;
    @Column(name = "ws_acct_balance")
    private int accountBalance;
    @Column(name = "ws_acct_crdate")
    private LocalDate creationDate;
    @Column(name = "ws_acct_lasttrdate")
    private LocalDate lastTransactionDate;
    @Column(name = "ws_acct_duration")
    private long accountDuration;
}
