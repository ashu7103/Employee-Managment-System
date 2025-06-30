package com.example.ems.Model;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity(name = "LOGIN_MASTER")
public class Users {
    @Id
//    @Column(name = "USERID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE", length = 20)
    private String role;

}
