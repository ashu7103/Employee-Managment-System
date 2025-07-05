package com.example.ems.Service;

import com.example.ems.Model.Department;
import com.example.ems.Model.Employee;
import com.example.ems.Model.StatusReport;
import com.example.ems.Repository.DepartmentRepository;
import com.example.ems.Repository.EmployeesRepository;
import com.example.ems.Repository.StatusReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeesService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeesService.class);

    @Autowired
    private EmployeesRepository employeesRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    // Save or Update Employee
    public Employee saveEmployee(Employee employee) {
        try {
            Employee saved = employeesRepository.save(employee);
            logger.info("Employee saved successfully with ID: {}", saved.getEmpId());
            return saved;
        } catch (Exception e) {
            logger.error("Error saving Employee: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save employee.");
        }
    }

    // Get all Employee
    public List<Employee> getAllEmployees() {
        try {
            List<Employee> list = employeesRepository.findAll();
            logger.info("Fetched {} employees.", list.size());
            return list;
        } catch (Exception e) {
            logger.error("Error fetching employee list: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch employees.");
        }
    }

    // Get Employee by ID
    public Employee getEmployeeById(Long empId) {
        try {
            Optional<Employee> empOpt = employeesRepository.findById(empId);
            if (empOpt.isPresent()) {
                logger.info("Employee found with ID: {}", empId);
                return empOpt.get();
            } else {
                logger.warn("Employee not found with ID: {}", empId);
                throw new RuntimeException("Employee not found.");
            }
        } catch (Exception e) {
            logger.error("Error fetching Employee by ID {}: {}", empId, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch employee.");
        }
    }

    // Delete Employee by ID
    public void deleteEmployee(Long empId) {
        try {
            if (employeesRepository.existsById(empId)) {
                employeesRepository.deleteById(empId);
                logger.info("Employee deleted with ID: {}", empId);
            } else {
                logger.warn("Employee not found to delete with ID: {}", empId);
                throw new RuntimeException("Employee not found.");
            }
        } catch (Exception e) {
            logger.error("Error deleting Employee by ID {}: {}", empId, e.getMessage(), e);
            throw new RuntimeException("Failed to delete employee.");
        }
    }

    public void updateEmployee(Employee updatedEmployee, Long id) {

        Employee existingEmployee = employeesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        // Fetch full Department entity using only the id from updatedEmployee.department
        Long departmentId = updatedEmployee.getDepartment().getDepartmentId();
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));

        // Update fields
        existingEmployee.setFirstName(updatedEmployee.getFirstName());
        existingEmployee.setLastName(updatedEmployee.getLastName());
        existingEmployee.setDob(updatedEmployee.getDob());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setDepartment(department);

        employeesRepository.save(existingEmployee);
    }
    @Autowired
    StatusReportRepository statusReportRepository;


    public Optional<Employee> getEmployeeByEmail(String email) {
        return Optional.ofNullable(employeesRepository.getEmployeeByEmail(email).orElse(null));
    }

    public List<StatusReport> getStatusReportByCompliance(Long complianceId) {
        return statusReportRepository.findByCompliance_ComplianceIdOrderByCreateDateDescStatusRptIdDesc(complianceId);
    }

    public List<StatusReport> getStatusReportByComplianceAndEmployee(Long complianceId, Long empId) {
        return statusReportRepository.findByCompliance_complianceIdAndEmployee_empIdOrderByCreateDateDescStatusRptIdDesc(complianceId,empId);
    }
}
