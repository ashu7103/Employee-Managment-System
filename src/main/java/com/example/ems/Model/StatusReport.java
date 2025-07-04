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
@Table(name = "STATUSREPORT")
public class StatusReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATUSRPTID")
    private Long statusRptId;

    @ManyToOne
    @JoinColumn(name = "COMPLIANCEID")
    private Compliance compliance;

    @ManyToOne
    @JoinColumn(name = "EMPID")
    private Employee employee;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "CREATEDATE")
    private LocalDate createDate;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;

    // Getters and Setters
}
