package com.example.ems.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/home")
    public String userHome(Model model, HttpSession session, Principal principal) {
        String username = principal.getName(); // Auto-fetched username
        model.addAttribute("username", username);
        return  "userHome";
    }

    // View Regulations Assigned to User
    @GetMapping("/regulations")
    public String viewAssignedRegulations() {
        return "viewUserRegulations";
    }

    // View and Update Comments on Regulations
    @GetMapping("/comments")
    public String viewComments() {
        return "viewComments";
    }

    @PostMapping("/comments/update")
    public String updateComments(/* parameters */) {
        return "redirect:/user/comments";
    }

    // Track Department Compliance Status
    @GetMapping("/compliance-status")
    public String viewComplianceStatus() {
        return "viewComplianceStatus";
    }
}
