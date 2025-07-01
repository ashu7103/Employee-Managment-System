package com.example.ems.Service;

import com.example.ems.Model.Compliance;
import com.example.ems.Repository.ComplianceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class ComplianceService {

    private static final Logger logger = LoggerFactory.getLogger(ComplianceService.class);

    @Autowired
    private ComplianceRepository complianceRepository;

    // Save or Update Compliance
    public Compliance saveCompliance(Compliance compliance) {
        try {
            Compliance saved = complianceRepository.save(compliance);
            logger.info("Compliance saved successfully with ID: {}", saved.getComplianceId());
            return saved;
        } catch (Exception e) {
            logger.error("Error saving Compliance: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save compliance.");
        }
    }

    // Get All Compliance Records
    public List<Compliance> getAllCompliance() {
        try {
            List<Compliance> list = complianceRepository.findAll();
            logger.info("Fetched {} compliance records.", list.size());
            return list;
        } catch (Exception e) {
            logger.error("Error fetching compliance list: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch compliance list.");
        }
    }

    // Get Compliance by ID
    public Compliance getComplianceById(Long complianceId) {
        try {
            Optional<Compliance> complianceOpt = complianceRepository.findById(complianceId);
            if (complianceOpt.isPresent()) {
                logger.info("Compliance found with ID: {}", complianceId);
                return complianceOpt.get();
            } else {
                logger.warn("Compliance not found with ID: {}", complianceId);
                throw new RuntimeException("Compliance not found.");
            }
        } catch (Exception e) {
            logger.error("Error fetching Compliance by ID {}: {}", complianceId, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch compliance.");
        }
    }

    // Delete Compliance by ID
    public void deleteCompliance(Long complianceId) {
        try {
            if (complianceRepository.existsById(complianceId)) {
                complianceRepository.deleteById(complianceId);
                logger.info("Compliance deleted with ID: {}", complianceId);
            } else {
                logger.warn("Compliance not found to delete with ID: {}", complianceId);
                throw new RuntimeException("Compliance not found.");
            }
        } catch (Exception e) {
            logger.error("Error deleting Compliance by ID {}: {}", complianceId, e.getMessage(), e);
            throw new RuntimeException("Failed to delete compliance.");
        }
    }
    public void updateCompliance(Compliance updatedCompliance) {
        Compliance existing = complianceRepository.findById(updatedCompliance.getComplianceId())
                .orElseThrow(() -> new RuntimeException("Compliance not found with ID: " + updatedCompliance.getComplianceId()));

        // Only update fields, not ID
        existing.setRlType(updatedCompliance.getRlType());
        existing.setDetails(updatedCompliance.getDetails());
        existing.setCreateDate(updatedCompliance.getCreateDate());
        existing.setDepartment(updatedCompliance.getDepartment());

        complianceRepository.save(existing);
    }

}
