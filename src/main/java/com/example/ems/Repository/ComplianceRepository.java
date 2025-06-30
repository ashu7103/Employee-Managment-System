package com.example.ems.Repository;

import com.example.ems.Model.Compliance;
import com.example.ems.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplianceRepository extends JpaRepository<Compliance, Long> {

}