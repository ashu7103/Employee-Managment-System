package com.example.ems.Controller;

import java.util.Optional;
import com.example.ems.Model.Compliance;
import com.example.ems.Model.Department;
import com.example.ems.Model.Employee;
import com.example.ems.Model.StatusReport;
import com.example.ems.Repository.ComplianceRepository;
import com.example.ems.Service.ComplianceService;
import com.example.ems.Service.DepartmentService;
import com.example.ems.Service.EmployeesService;
import com.example.ems.Service.StatusReportService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    EmployeesService employeesService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    private ComplianceService complianceService;
    @Autowired
    private StatusReportService statusReportService;

    @GetMapping("/home")
    public String adminHome(Model model, HttpSession session, Principal principal) {
        String username = principal.getName(); // Auto-fetched username
        model.addAttribute("username", username);
        return  "adminHome";
    }


    //------------------Employee Management------------------------------

    @GetMapping("/employees/add")
    public String addEmployeeForm(Model model) {
        model.addAttribute("employee",new Employee());
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "addEmployee";
    }

    @PostMapping("/employees/add")
    public String saveEmployee(@ModelAttribute Employee employee) {
        Department dept = departmentService.getDepartmentById(employee.getDepartment().getDepartmentId());
        employee.setDepartment(dept);
        employeesService.saveEmployee(employee);
        return "redirect:/admin/employees";
    }

    @GetMapping("/employees")
    public String viewEmployees(Model model) {
        List<Employee> emp = employeesService.getAllEmployees();
        model.addAttribute("empList",emp);
        return "employees";
    }

    @GetMapping("/employees/edit/{id}")
    public String updateEmployeeForm(Model model,@PathVariable Long id) {
        Employee employee= employeesService.getEmployeeById(id);
        model.addAttribute("employee",employee);
//        model.addAttribute("empId",id);
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "editEmployee";
    }
    @PostMapping("/employees/edit/{id}")
    public String updateEmployees(@ModelAttribute Employee employee,@PathVariable Long id){
        employeesService.updateEmployee(employee,id);
        return "redirect:/admin/employees";
    }
    @GetMapping("/employees/delete/{id}")
    public String deleteEmployees(@PathVariable Long id){
        employeesService.deleteEmployee(id);
        return "redirect:/admin/employees";
    }

    //--------------Department Management-----------------------------------
    @GetMapping("/departments/add")
    public String addDepartmentForm(Model model) {
        model.addAttribute("department", new Department());
        return "addDepartment";
    }

    @PostMapping("/departments/add")
    public String saveDepartment(@ModelAttribute Department department) {
        departmentService.saveDepartment(department);
        return "redirect:/admin/departments";
    }

    @GetMapping("/departments")
    public String viewDepartments(Model model) {
        model.addAttribute("departments",departmentService.getAllDepartments());
        return "departments";
    }

    @GetMapping("/departments/edit/{id}")
    public String updatedepartmentForm(Model model,@PathVariable Long id) {
        Department department= departmentService.getDepartmentById(id);
        model.addAttribute("department",department);
        return "editdepartment";
    }
    @PostMapping("/departments/edit/{id}")
    public String updatedepartments(@ModelAttribute Department department,@PathVariable Long id){
        departmentService.updateDepartment(department,id);
        return "redirect:/admin/departments";
    }
    @GetMapping("/departments/delete/{id}")
    public String deletedepartments(@PathVariable Long id){
        departmentService.deleteDepartment(id);
        return "redirect:/admin/departments";
    }

    //-----------Compliance - Regulation Creation-------------------
    // Show All Compliance
    @GetMapping("/compliances")
    public String viewAllCompliance(Model model) {
        model.addAttribute("complianceList", complianceService.getAllCompliance());
        return "compliances";
    }

    // Show Add Form
    @GetMapping("/compliance/add")
    public String showAddComplianceForm(Model model) {
        model.addAttribute("compliance", new Compliance());
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "addCompliance";
    }

    // Handle Add Form
    @PostMapping("/compliance/add")
    public String saveCompliance(@ModelAttribute Compliance compliance) {
        Department dept = departmentService.getDepartmentById(compliance.getDepartment().getDepartmentId());
        compliance.setDepartment(dept);
        complianceService.saveCompliance(compliance);
        return "redirect:/admin/compliances";
    }

    // Show Edit Form

    @GetMapping("/compliance/edit/{id}")
    public String editCompliance(@PathVariable Long id, Model model) {
        Compliance compliance = complianceService.getComplianceById(id);
        if (compliance == null) {
            throw new RuntimeException("Compliance not found with ID: " + id);
        }
        model.addAttribute("compliance", compliance);
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "editCompliance";
    }

    // Handle Update
    @PostMapping("/compliance/edit")
    public String updateCompliance(@ModelAttribute Compliance updatedCompliance) {
        Department dept = departmentService.getDepartmentById(updatedCompliance.getDepartment().getDepartmentId());
        Compliance existing=complianceService.getComplianceById(updatedCompliance.getComplianceId());
        existing.setDepartment(dept);

        complianceService.updateCompliance(updatedCompliance);
        return "redirect:/admin/compliances";
    }

    // Delete Compliance
    @GetMapping("/compliance/delete/{id}")
    public String deleteCompliance(@PathVariable Long id) {
        try {
            complianceService.deleteCompliance(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting compliance with ID: " + id);
        }
        return "redirect:/admin/compliances";
    }


    //-----------------StstusReport Managment---------------------------
    @GetMapping("statusReports")
    public String viewAllReports(Model model) {
        model.addAttribute("statusReportList", statusReportService.getAllStatusReports());
        return "statusReports";
    }

    @GetMapping("/statusReports/add")
    public String showAddForm(Model model) {
        model.addAttribute("statusReport", new StatusReport());
        model.addAttribute("complianceList", complianceService.getAllCompliance());
        model.addAttribute("employeeList", employeesService.getAllEmployees());
        model.addAttribute("departmentList", departmentService.getAllDepartments());
        return "addStatusReport";
    }

    @PostMapping("/statusReports/add")
    public String saveReport(@ModelAttribute StatusReport report) {
        statusReportService.saveStatusReport(report);
        return "redirect:/admin/statusReports";
    }

    @GetMapping("/statusReports/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        StatusReport report = statusReportService.getStatusReportById(id);
        model.addAttribute("statusReport", report);
        model.addAttribute("complianceList", complianceService.getAllCompliance());
        model.addAttribute("employeeList", employeesService.getAllEmployees());
        model.addAttribute("departmentList", departmentService.getAllDepartments());
        return "addStatusReport";
    }

    @GetMapping("/statusReports/delete/{id}")
    public String deleteReport(@PathVariable Long id) {
        statusReportService.deleteStatusReport(id);
        return "redirect:/admin/statusReports";
    }
}
