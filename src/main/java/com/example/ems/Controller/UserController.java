package com.example.ems.Controller;

import com.example.ems.Model.Compliance;
import com.example.ems.Model.Employee;
import com.example.ems.Model.StatusReport;
import com.example.ems.Repository.StatusReportRepository;
import com.example.ems.Service.ComplianceService;
import com.example.ems.Service.EmployeesService;
import com.example.ems.Service.StatusReportService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    EmployeesService employeesService;
    @Autowired
    ComplianceService complianceService;
    @Autowired
    private StatusReportService statusReportService;

    @GetMapping("/home")
    public String userHome(Model model, HttpSession session, Principal principal) {
        String username = principal.getName(); // Auto-fetched username
        model.addAttribute("username", username);
        return  "userHome";
    }
    //------------------------------------------------------------
    @GetMapping("/View/AssignedRegulation")
    public String RegulationStatusByDept(Model model){
        List<Employee> employeeList=employeesService.getAllEmployees();
        model.addAttribute("employeeList",employeeList);
        return "userViewAssignedRegulation";
    }

    @GetMapping("/userViewAssignedRegulation")
    public String viewRegulationsByDepartment(@RequestParam Long empId, Model model) {
        List<Employee> employeeList=new ArrayList<>() ;
        employeeList.add(employeesService.getEmployeeById(empId));

        model.addAttribute("employeeList",employeeList);
        model.addAttribute("selectedEmpId",empId);
        List<Compliance> complianceList = statusReportService.getComplianceByEmployeeId(empId);

        model.addAttribute("regulations", complianceList);
//        model.addAttribute("department", departmentService.getDepartmentById(deptId));

        return "userViewAssignedRegulation";
    }
    //------------------------------------------------------------

    @GetMapping("/regulation/comment/{compId}/{selectedEmpId}")
    public String commentOnRegulationStatus(@PathVariable Long compId,
                                            @PathVariable Long selectedEmpId,
                                            Principal principal,
                                            Model model) {

        String email = principal.getName();
        Optional<Employee> loggedInEmployeeOpt = employeesService.getEmployeeByEmail(email);

        if (loggedInEmployeeOpt.isEmpty()) {
            model.addAttribute("WrongUser", "User doesnt have access to modify this comment.");
            return "access-denied";
        }

        StatusReport report = statusReportService.getStatusReportByComplianceAndEmployee(compId, selectedEmpId);

        // Check if logged-in user is same as the StatusReport's employee
        if (!report.getEmployee().getEmail().equals(email)) {
            model.addAttribute("WrongUser", "You are not authorized to modify this status.");
            return "access-denied";
        }
        Employee employee = loggedInEmployeeOpt.get();
        Compliance compliance = complianceService.getComplianceById(compId);


        // Create new StatusReport
        StatusReport newReport = new StatusReport();
        newReport.setEmployee(employee);
        newReport.setCompliance(compliance);
        newReport.setDepartment(employee.getDepartment()); // assuming employee has department
        newReport.setComments(""); // or any default
        newReport.setCreateDate(LocalDate.now()); // or LocalDateTime.now()

//        statusReportService.saveStatusReport(newReport);

        model.addAttribute("report", newReport);
        model.addAttribute("statusRptId", report.getStatusRptId());
        return "updateComment";
    }
    @PostMapping("/statusreport/updateComment")
    public String updateComment(@RequestParam Long statusRptId, @RequestParam String comments, Principal principal, Model model) {

        StatusReport report = statusReportService.getStatusReportById(statusRptId);
        if (report == null) {
            model.addAttribute("error", "Status Report not found.");
            return "error";
        }

        // Optional: Check logged-in user matches report's employee
        String email = principal.getName();
        if (!report.getEmployee().getEmail().equals(email)) {
            model.addAttribute("error", "Unauthorized to update this comment.");
            return "access-denied";
        }

        report.setComments(comments);
        report.setCreateDate(LocalDate.now()); // or LocalDateTime.now()

        statusReportService.saveStatusReport(report);

        return "redirect:/user/View/AssignedRegulation"; // Redirect as per your flow
    }




    @GetMapping("/UserLog/{complianceId}/{empId}")
    public String viewUserLogs(@PathVariable Long complianceId, @PathVariable Long empId,  Principal principal,
                               Model model) {

        String email = principal.getName();
        Optional<Employee> loggedInEmployeeOpt = employeesService.getEmployeeByEmail(email);

        if (loggedInEmployeeOpt.isEmpty()) {
            model.addAttribute("WrongUser", "User doesnt have access to modify this comment.");
            return "access-denied";
        }

        StatusReport report = statusReportService.getStatusReportByComplianceAndEmployee(complianceId
                , empId);

//        if (report == null) {
//            model.addAttribute("error", "No status report found for this regulation.");
//            return "error";
//        }

        // Check if logged-in user is same as the StatusReport's employee
        if (!report.getEmployee().getEmail().equals(email)) {
            model.addAttribute("WrongUser", "You are not authorized to modify this status.");
            return "access-denied";
        }
        List<StatusReport> logs = employeesService.getStatusReportByComplianceAndEmployee(complianceId, empId);

        model.addAttribute("statusReports", logs);
        model.addAttribute("employee", employeesService.getEmployeeById(empId));
        model.addAttribute("compliance", complianceService.getComplianceById(complianceId));

        return "userLogs";
    }

    @GetMapping("/previousStatusReport/{complianceId}/{empId}")
    public String viewAllLogsForCompliance(@PathVariable Long complianceId, @PathVariable Long empId, Principal principal,
                                           Model model) {
        String email = principal.getName();
        Optional<Employee> loggedInEmployeeOpt = employeesService.getEmployeeByEmail(email);

        if (loggedInEmployeeOpt.isEmpty()) {
            model.addAttribute("WrongUser", "User doesnt have access to modify this comment.");
            return "access-denied";
        }
        StatusReport report = statusReportService.getStatusReportByComplianceAndEmployee(complianceId, empId);
        // Check if logged-in user is same as the StatusReport's employee
        if (!report.getEmployee().getEmail().equals(email)) {
            model.addAttribute("WrongUser", "You are not authorized to modify this status.");
            return "access-denied";
        }
        List<StatusReport> logs = employeesService.getStatusReportByCompliance(complianceId);

        model.addAttribute("statusReports", logs);
        model.addAttribute("employee", employeesService.getEmployeeById(empId));
        model.addAttribute("compliance", complianceService.getComplianceById(complianceId));

        return "previousStatusReport";
    }




}
