package com.example.ems;

import jakarta.servlet.http.HttpSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;

@SpringBootApplication
//@RestController
@Controller
public class EmployeeManagmentSystemApplication {

	public static void main(String[] args) {
		System.out.println(" Started ");
		SpringApplication.run(EmployeeManagmentSystemApplication.class, args);
		System.out.println(" Ended ");
	}
 	@GetMapping("/check")
	String check(){
		return "test runned succesfully";
	}




}
