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
@Table(name = "COMPLIANCE")
public class Compliance {

    @Id
    @Column(name = "COMPLIANCEID")
    private Long complianceId;

    @Column(name = "RLTYPE", length = 15)
    private String rlType;

    @Column(name = "DETAILS", length = 250)
    private String details;

    @Column(name = "CREATEDATE")
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;

    // Getters and Setters
}
