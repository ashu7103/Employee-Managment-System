package com.example.ems.Service;

import com.example.ems.Model.Department;
import com.example.ems.Repository.DepartmentRepository;
import com.example.ems.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    // Create or Update Department
    public Department saveDepartment(Department department) {
        try {
            Department saved = departmentRepository.save(department);
            logger.info("Department saved successfully with ID: {}", saved.getDepartmentId());
            return saved;
        } catch (Exception e) {
            logger.error("Error saving Department: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save department.");
        }
    }

    // Get all Departments
    public List<Department> getAllDepartments() {
        try {
            List<Department> list = departmentRepository.findAll();
            logger.info("Fetched {} departments.", list.size());
            return list;
        } catch (Exception e) {
            logger.error("Error fetching department list: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch departments.");
        }
    }

    // Get Department by ID
    public Department getDepartmentById(Long departmentId) {
        try {
            Optional<Department> deptOpt = departmentRepository.findById(departmentId);
            if (deptOpt.isPresent()) {
                logger.info("Department found with ID: {}", departmentId);
                return deptOpt.get();
            } else {
                logger.warn("Department not found with ID: {}", departmentId);
                throw new RuntimeException("Department not found.");
            }
        } catch (Exception e) {
            logger.error("Error fetching Department by ID {}: {}", departmentId, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch department.");
        }
    }

    // Delete Department by ID
    public void deleteDepartment(Long departmentId) {
        try {
            if (departmentRepository.existsById(departmentId)) {
                departmentRepository.deleteById(departmentId);
                logger.info("Department deleted with ID: {}", departmentId);
            } else {
                logger.warn("Department not found to delete with ID: {}", departmentId);
                throw new RuntimeException("Department not found.");
            }
        } catch (Exception e) {
            logger.error("Error deleting Department by ID {}: {}", departmentId, e.getMessage(), e);
            throw new RuntimeException("Failed to delete department.");
        }
    }

    public void updateDepartment(Department updatedDepartment, Long id) {
        try {
            Department existingDepartment = departmentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));

            existingDepartment.setDepartmentName(updatedDepartment.getDepartmentName());

            departmentRepository.save(existingDepartment);

            logger.info("Department updated successfully with id: {}", id);
        } catch (Exception ex) {
            logger.error("Unexpected error while updating department with id {}: {}", id, ex.getMessage());
            throw new RuntimeException("Failed to update department. Please try again later.");
        }
    }
}
