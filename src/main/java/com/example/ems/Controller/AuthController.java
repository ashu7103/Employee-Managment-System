package com.example.ems.Controller;

import com.example.ems.Model.Users;
import com.example.ems.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("home")
    public String homePage(Model model, HttpSession session) {
        String sessionId=session.getId();
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        model.addAttribute("sessionId",sessionId);

        long creationTime = session.getCreationTime();           // when session started (ms)
        int maxInactive = session.getMaxInactiveInterval();      // seconds
        long expireTime = creationTime + (maxInactive * 1000L);  // estimated expiry time in ms

        model.addAttribute("creationTime", new Date(creationTime));
        model.addAttribute("expireTime", new Date(expireTime));
        return "home";  // Looks for home.html in templates
    }

    @GetMapping("/login")
    public String loginPage() {
        logger.info("Login page requested");
        return "login";
    }

//-------- Logout define later in better way-------------------
    @GetMapping("/logout-manual")
    public String logoutManual(HttpSession session) {
//        session.invalidate();  // Destroys session
        return "redirect:/perform_logout";
    }


    @GetMapping("/access-denied")
    public String accessDeniedPage() {
        return "access-denied";  // Show access-denied.html
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
//        if (error != null) {
//            model.addAttribute("errorInRegister", error);
//        }
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Users user, RedirectAttributes redirectAttributes) {
        logger.info("Registration attempt for username: {}", user.getUsername());

        try {
            userService.save(user);
            logger.info("User registered successfully: {}", user.getUsername());
            return "redirect:/login?success";
        } catch (Exception e) {
//            model.addAttribute("errorInRegister", e.getMessage());
            logger.error("Registration failed for username: {}, reason: {}", user.getUsername(), e.getMessage());
//            redirectAttributes.addFlashAttribute("errorInRegister", e.getMessage());
            redirectAttributes.addFlashAttribute("errorInRegister","username alredy exist in System! Try Another");
            return "redirect:/register";
        }


    }

}