package com.example.ems.Repository;

import com.example.ems.Model.StatusReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusReportRepository extends JpaRepository<StatusReport,Long> {
}
