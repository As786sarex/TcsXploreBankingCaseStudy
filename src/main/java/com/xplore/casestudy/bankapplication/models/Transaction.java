package com.xplore.casestudy.bankapplication.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ws_cust_id")
    private Long transactionId;
    @Column(name = "ws_accnt_type")
    private String accountType;
    @Column(name = "ws_amt")
    private int amount;
    @Column(name = "ws_trxn_date")
    private LocalDate transactionDate;
    private String transactionType;
    @Column(name = "ws_src_typ")
    private String sourceAccountType;
    @Column(name = "ws_tgt_typ")
    private String targetAccountType;
    private Long sourceAccountId;
    private Long targetAccountId;
}
