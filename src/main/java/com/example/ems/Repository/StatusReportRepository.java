package com.example.ems.Repository;

import com.example.ems.Model.Compliance;
import com.example.ems.Model.StatusReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusReportRepository extends JpaRepository<StatusReport,Long> {
    @Query("SELECT COUNT(s) FROM StatusReport s WHERE s.department.departmentId = :deptId AND s.compliance.complianceId = :rlId")
    long countByComplianceAndDepartment(@Param("rlId") Long rlId, @Param("deptId") Long deptId);

//    @Query("SELECT sr.compliance FROM StatusReport sr WHERE sr.employee.employeeId = :empId")
//    List<Compliance> getComplianceByEmployeeId(@Param("empId") Long empId);
    @Query("SELECT s.compliance FROM StatusReport s WHERE s.employee.id = :empId")
    List<Compliance> getComplianceByEmployeeId(@Param("empId") Long empId);

    @Query("""
    SELECT c, sr FROM Compliance c
                 JOIN c.department d
                 LEFT JOIN StatusReport sr ON sr.compliance.id = c.id
                     AND sr.department.id = :deptId
                     AND sr.createDate = (
                         SELECT MAX(s2.createDate) FROM StatusReport s2
                         WHERE s2.compliance.id = c.id AND s2.department.id = :deptId
                     )
                 WHERE d.id = :deptId
    """)
    List<Object[]> latesStatusOfAllComplianceForDept(@Param("deptId") Long deptId);

//    StatusReport findByComplianceComplianceIdAndEmployeeEmpId(Long compId, Long empId);

    StatusReport findTopByCompliance_complianceIdAndEmployee_empIdOrderByCreateDateDesc(Long compId, Long empId);

    // 1. For User-specific Logs
    List<StatusReport> findByCompliance_complianceIdAndEmployee_empIdOrderByCreateDateDescStatusRptIdDesc(Long complianceId, Long empId);

    // 2. For All Logs of that Compliance
    List<StatusReport> findByCompliance_ComplianceIdOrderByCreateDateDescStatusRptIdDesc(Long complianceId);

}
