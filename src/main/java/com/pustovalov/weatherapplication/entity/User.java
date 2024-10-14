package com.pustovalov.weatherapplication.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "login", length = 20, nullable = false, unique = true)
    String login;

    @Column(name = "password", length = 60, nullable = false)
    String password;
}
