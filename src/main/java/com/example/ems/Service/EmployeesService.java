package com.example.ems.Service;

import com.example.ems.Model.Employees;
import com.example.ems.Repository.EmployeesRepository;
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

    // Save or Update Employee
    public Employees saveEmployee(Employees employee) {
        try {
            Employees saved = employeesRepository.save(employee);
            logger.info("Employee saved successfully with ID: {}", saved.getEmpId());
            return saved;
        } catch (Exception e) {
            logger.error("Error saving Employee: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save employee.");
        }
    }

    // Get all Employees
    public List<Employees> getAllEmployees() {
        try {
            List<Employees> list = employeesRepository.findAll();
            logger.info("Fetched {} employees.", list.size());
            return list;
        } catch (Exception e) {
            logger.error("Error fetching employee list: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch employees.");
        }
    }

    // Get Employee by ID
    public Employees getEmployeeById(Long empId) {
        try {
            Optional<Employees> empOpt = employeesRepository.findById(empId);
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
}
