package com.example.ems.Model;

import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "STATUSRPTID")
    private Long statusRptId;

    @ManyToOne
    @JoinColumn(name = "COMPLIANCEID")
    private Compliance compliance;

    @ManyToOne
    @JoinColumn(name = "EMPID")
    private Employees employee;

    @Column(name = "COMMENTS", length = 15)
    private String comments;

    @Column(name = "CREATEDATE")
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;

    // Getters and Setters
}
