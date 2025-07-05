package com.example.ems.Service;

import com.example.ems.Model.Compliance;
import com.example.ems.Model.Department;
import com.example.ems.Model.Employee;
import com.example.ems.Model.StatusReport;
import com.example.ems.Repository.StatusReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class StatusReportService {

    private static final Logger logger = LoggerFactory.getLogger(StatusReportService.class);

    @Autowired
    private StatusReportRepository statusReportRepository;

    // Save or Update StatusReport
    public StatusReport saveStatusReport(StatusReport statusReport) {
        try {
            StatusReport saved = statusReportRepository.save(statusReport);
            logger.info("StatusReport saved successfully with ID: {}", saved.toString());
            return saved;
        } catch (Exception e) {
            logger.error("Error saving StatusReport: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save status report.");
        }
    }

    // Get all StatusReports
    public List<StatusReport> getAllStatusReports() {
        try {
            List<StatusReport> list = statusReportRepository.findAll();
            logger.info("Fetched {} status reports.", list.size());
            return list;
        } catch (Exception e) {
            logger.error("Error fetching status report list: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch status reports.");
        }
    }

    // Get StatusReport by ID
    public StatusReport getStatusReportById(Long statusRptId) {
        try {
            Optional<StatusReport> reportOpt = statusReportRepository.findById(statusRptId);
            if (reportOpt.isPresent()) {
                logger.info("StatusReport found with ID: {}", reportOpt.get().toString());
                return reportOpt.get();
            } else {
                logger.warn("StatusReport not found with ID: {}", statusRptId);
                throw new RuntimeException("Status report not found.");
            }
        } catch (Exception e) {
            logger.error("Error fetching StatusReport by ID {}: {}", statusRptId, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch status report.");
        }
    }

    // Delete StatusReport by ID
    public void deleteStatusReport(Long statusRptId) {
        try {
            if (statusReportRepository.existsById(statusRptId)) {
                statusReportRepository.deleteById(statusRptId);
                logger.info("StatusReport deleted with ID: {}", statusRptId);
            } else {
                logger.warn("StatusReport not found to delete with ID: {}", statusRptId);
                throw new RuntimeException("Status report not found.");
            }
        } catch (Exception e) {
            logger.error("Error deleting StatusReport by ID {}: {}", statusRptId, e.getMessage(), e);
            throw new RuntimeException("Failed to delete status report.");
        }
    }
    // Update Existing
    public void updateStatusReport(StatusReport updatedReport, Long id) {
        try {
            StatusReport existingReport = statusReportRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Status Report not found with id: " + id));

            validateAndAttachRelations(updatedReport);

            existingReport.setCompliance(updatedReport.getCompliance());
            existingReport.setEmployee(updatedReport.getEmployee());
            existingReport.setDepartment(updatedReport.getDepartment());
            existingReport.setComments(updatedReport.getComments());
            existingReport.setCreateDate(updatedReport.getCreateDate());

            statusReportRepository.save(existingReport);
            logger.info("Status Report updated successfully with id: {}", id);

        } catch (Exception ex) {
            logger.error("Error updating Status Report: {}", ex.getMessage());
            throw new RuntimeException("Failed to update Status Report");
        }
    }
    @Autowired
    EmployeesService employeesService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    ComplianceService complianceService;
    // Validate and Attach Full Relations (Compliance, Employee, Department)
    private void validateAndAttachRelations(StatusReport report) {

        Compliance compliance = complianceService.getComplianceById(report.getCompliance().getComplianceId());
        if (compliance == null) {
            throw new RuntimeException("Compliance not found with id: " + report.getCompliance().getComplianceId());
        }

        Employee employee = employeesService.getEmployeeById(report.getEmployee().getEmpId());
        if (employee == null) {
            throw new RuntimeException("Employee not found with id: " + report.getEmployee().getEmpId());
        }

        Department department = departmentService.getDepartmentById(report.getDepartment().getDepartmentId());
        if (department == null) {
            throw new RuntimeException("Department not found with id: " + report.getDepartment().getDepartmentId());
        }

        report.setCompliance(compliance);
        report.setEmployee(employee);
        report.setDepartment(department);
    }

    public List<Compliance> getComplianceByEmployeeId(Long empId) {
        return statusReportRepository.getComplianceByEmployeeId(empId);
    }

    public List<Object[]> latesStatusOfAllComplianceForDept(Long deptId) {
        //  object[0] = compliance
        //  object[1] = statusReport
        return statusReportRepository.latesStatusOfAllComplianceForDept(deptId);
    }

    public StatusReport getStatusReportByComplianceAndEmployee(Long compId, Long empId) {
        return statusReportRepository.findTopByCompliance_complianceIdAndEmployee_empIdOrderByCreateDateDesc(compId, empId);
    }

}
