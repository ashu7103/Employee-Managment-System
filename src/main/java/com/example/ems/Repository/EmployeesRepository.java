package com.example.ems.Repository;

import com.example.ems.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeesRepository extends JpaRepository<Employee,Long> {
//    long countByDepartment(Long deptId);
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.department.id = :deptId")
    long countByDepartment(@Param("deptId") Long deptId);

    Optional<Employee> getEmployeeByEmail(String email);
}
