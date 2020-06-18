package com.xplore.casestudy.bankapplication.models;

import lombok.*;

import javax.persistence.*;

@Entity(name = "users")
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String password;
    private String roles;
    private long timestamp;

}
