## 🚀 Employee Management System (EMS) — Role-Based Secure Web Application

A robust, real-world **Employee Management System** designed with full backend layering, secure login/register system, encrypted password storage, and strict role-based access — all built using industry-standard tools and frameworks.

---

## 🛠️ **Tech Stack**

* **Backend:** Java, Spring Boot, Spring MVC, Spring Data JPA, Spring Security
* **Frontend:** Thymeleaf, Bootstrap 5
* **Database:** MySQL
* **Security:** BCrypt Password Encryption, Role-Based Access Control
* **Utilities:** Lombok, Logback Logger

---

## 🔒 **Key Features**

✅ **Role-Based Login & Redirection**

* Manual, custom-built Login and Registration pages
* User credentials stored securely in the database
* Passwords encrypted with BCrypt hashing
* Post-login redirection based on role:

  * **Admin** → Admin Dashboard
  * **Employee** → Employee Portal

✅ **Admin Functionalities**

* Add, Update, Delete Departments
* Manage Employees
* Create and Track Regulations (Compliance)
* Monitor Compliance Status Reports

✅ **Employee Functionalities**

* View Assigned Regulations
* Submit Status Reports with Comments & Dates
* Track Compliance status for assigned departments

✅ **Backend Architecture**

* Clean 3-Layer Structure:
  `Controller → Service → Repository`
* Entity Relationships with proper `Many-to-One` mappings:

  * Employee → Department
  * Compliance → Department
  * StatusReport → Employee, Compliance, Department

✅ **Security & Session Management**

* Fully integrated with Spring Security
* Passwords encrypted, no plain-text storage
* Session-controlled access based on roles

---

## 📂 **Project Structure Overview**

```bash
com.example.ems
│
├── Controller       # Handles Web requests and page mappings
├── Service          # Business logic layer
├── Repository       # Data access layer (JPA Repositories)
├── Model            # Entities (Employee, Department, Compliance, StatusReport)
└── Security         # Configuration for Spring Security
```

---

## ⚙️ **Setup & Run**

1. **Database Setup:**

   * MySQL Database: `ems`
   * Create tables manually or let Spring Boot auto-generate

2. **Application Properties Example:**

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ems
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

3. **Run the Project:**

   ```bash
   mvn clean install  
   mvn spring-boot:run  
   ```

4. **Access:**

   * `http://localhost:8080/login` — Manual Login Page
   * Role-specific Homepages based on credentials

---

## 🎯 **Future Enhancements (Optional):**

* Pagination & Search functionality
* Email Notifications for Compliance updates
* Enhanced UI/UX improvements
* User Password Reset

---

## 💡 **Skills Demonstrated**

* Backend Development with Spring
* Secure Authentication & Authorization
* Role-Based UI Flow
* Realistic System Design Principles
* Data Handling with Spring Data JPA
* Password Encryption & Security Best Practices
* OS, DBMS, and Problem-Solving Fundamentals

---

## 🤝 **Contributing**

Open for suggestions, improvements, and feature additions. Feel free to fork and raise PRs!

---

## 📬 **Contact**

Connect on [LinkedIn](https://www.linkedin.com/in/ashu_7103/) for discussions, collaborations, or feedback.

