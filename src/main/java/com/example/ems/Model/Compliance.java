package com.example.ems.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "COMPLIANCE")
public class Compliance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPLIANCEID")
    private Long complianceId;

    @Column(name = "RLTYPE", length = 15)
    private String rlType;

    @Column(name = "DETAILS", length = 250)
    private String details;

    @Column(name = "CREATEDATE")
    private LocalDate createDate;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;

    // Getters and Setters
}
