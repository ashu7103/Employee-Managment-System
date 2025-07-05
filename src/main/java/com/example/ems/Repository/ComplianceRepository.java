package com.example.ems.Repository;

import com.example.ems.Model.Compliance;
import com.example.ems.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplianceRepository extends JpaRepository<Compliance, Long> {

    @Query("SELECT c FROM Compliance c WHERE c.department.departmentId = :deptId")
    List<Compliance> getComplianceByDepartment(@Param("deptId") Long deptId);


}