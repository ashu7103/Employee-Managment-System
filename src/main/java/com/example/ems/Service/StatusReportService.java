package com.example.ems.Service;

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
}
