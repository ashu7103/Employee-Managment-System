package com.example.ems.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "EMPLOYEES")
public class Employees {

    @Id
    @Column(name = "EMPID")
    private Long empId;

    @Column(name = "FIRSTNAME", length = 45)
    private String firstName;

    @Column(name = "LASTNAME", length = 45)
    private String lastName;

    @Column(name = "DOB")
    private Date dob;

    @Email
    @Column(name = "EMAIL", length = 100)
    private String email;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;

    // Getters and Setters
}
