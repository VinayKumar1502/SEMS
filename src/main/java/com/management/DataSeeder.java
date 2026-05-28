package com.management;

import com.management.model.Employee;
import com.management.model.Student;
import com.management.repository.EmployeeRepository;
import com.management.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * DataSeeder - Runs on application startup to insert sample data.
 *
 * @Component - Makes this a Spring-managed bean
 * CommandLineRunner - run() method executes after app starts
 *
 * This gives you sample data to play with right away!
 */
@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) throws Exception {

        // ==========================================
        // Seed Students
        // Using setters instead of all-args constructor
        // to avoid type mismatch errors (int vs Integer, double vs Double)
        // ==========================================

        studentRepository.save(makeStudent("Arjun",   "Sharma",   "arjun.sharma@college.edu",   "9876543210", "2002-05-15", "Computer Science",        3, 8.5, "123 MG Road, Hyderabad",       "ACTIVE",    "2021-07-10"));
        studentRepository.save(makeStudent("Priya",   "Reddy",    "priya.reddy@college.edu",    "9876543211", "2001-08-22", "Electronics Engineering",  4, 9.1, "45 Banjara Hills, Hyderabad",  "ACTIVE",    "2020-07-10"));
        studentRepository.save(makeStudent("Rahul",   "Verma",    "rahul.verma@college.edu",    "9876543212", "2003-01-10", "Mechanical Engineering",   2, 7.8, "78 Jubilee Hills, Hyderabad",  "ACTIVE",    "2022-07-10"));
        studentRepository.save(makeStudent("Sneha",   "Gupta",    "sneha.gupta@college.edu",    "9876543213", "2000-11-30", "MBA",                      1, 8.9, "12 Hitech City, Hyderabad",    "ACTIVE",    "2023-07-10"));
        studentRepository.save(makeStudent("Kiran",   "Kumar",    "kiran.kumar@college.edu",    "9876543214", "1999-03-25", "Civil Engineering",        4, 6.5, "56 Secunderabad, Telangana",   "GRADUATED", "2019-07-10"));
        studentRepository.save(makeStudent("Lakshmi", "Devi",     "lakshmi.devi@college.edu",   "9876543215", "2002-09-12", "Data Science",             3, 9.4, "89 Gachibowli, Hyderabad",     "ACTIVE",    "2021-07-10"));

        // ==========================================
        // Seed Employees
        // ==========================================

        employeeRepository.save(makeEmployee("Suresh",  "Babu",     "suresh.babu@company.com",    "9123456780", "1985-04-10", "Engineering",    "Senior Developer",      85000.0, "2018-03-01", "101 Cyber Towers, Hyderabad",  "ACTIVE",   "Ramesh Kumar"));
        employeeRepository.save(makeEmployee("Ananya",  "Patel",    "ananya.patel@company.com",   "9123456781", "1990-07-22", "Human Resources","HR Manager",            70000.0, "2019-06-15", "202 HITEC City, Hyderabad",    "ACTIVE",   "CEO"));
        employeeRepository.save(makeEmployee("Vikram",  "Singh",    "vikram.singh@company.com",   "9123456782", "1988-12-05", "Finance",        "Finance Analyst",       65000.0, "2017-09-01", "303 Madhapur, Hyderabad",      "ACTIVE",   "Ananya Patel"));
        employeeRepository.save(makeEmployee("Deepa",   "Nair",     "deepa.nair@company.com",     "9123456783", "1992-02-18", "Engineering",    "Frontend Developer",    75000.0, "2020-01-10", "404 Kondapur, Hyderabad",      "ON_LEAVE", "Suresh Babu"));
        employeeRepository.save(makeEmployee("Ramesh",  "Kumar",    "ramesh.kumar@company.com",   "9123456784", "1980-06-30", "Management",     "CTO",                  150000.0, "2015-01-01", "505 Banjara Hills, Hyderabad", "ACTIVE",   "CEO"));
        employeeRepository.save(makeEmployee("Meena",   "Krishnan", "meena.krishnan@company.com", "9123456785", "1995-08-14", "Marketing",      "Marketing Executive",   55000.0, "2021-04-20", "606 Jubilee Hills, Hyderabad", "ACTIVE",   "Ananya Patel"));

        System.out.println(">>> Sample data loaded: 6 students, 6 employees");
    }

    /**
     * Helper method to build a Student using setters.
     *
     * WHY SETTERS INSTEAD OF ALL-ARGS CONSTRUCTOR?
     * -----------------------------------------------
     * @AllArgsConstructor generates: Student(Long, String, String, ..., Integer, Double, ...)
     * Passing literal  3  → Java sees primitive int, NOT Integer  → compile error
     * Passing literal  8.5 → Java sees primitive double, NOT Double → compile error
     *
     * Using setters avoids this entirely: setYear(3) auto-boxes int → Integer safely.
     * It also makes the code easier to read and immune to field reordering in the model.
     */
    private Student makeStudent(String firstName, String lastName, String email,
                                String phone, String dob, String course,
                                int year, double gpa,
                                String address, String status, String enrollmentDate) {
        Student s = new Student();           // uses @NoArgsConstructor
        s.setFirstName(firstName);
        s.setLastName(lastName);
        s.setEmail(email);
        s.setPhone(phone);
        s.setDateOfBirth(dob);
        s.setCourse(course);
        s.setYear(year);                     // int auto-boxed → Integer ✅
        s.setGpa(gpa);                       // double auto-boxed → Double ✅
        s.setAddress(address);
        s.setStatus(status);
        s.setEnrollmentDate(enrollmentDate);
        return s;
    }

    /**
     * Helper method to build an Employee using setters (same reason as above).
     */
    private Employee makeEmployee(String firstName, String lastName, String email,
                                  String phone, String dob, String department,
                                  String position, double salary,
                                  String joiningDate, String address,
                                  String status, String manager) {
        Employee e = new Employee();         // uses @NoArgsConstructor
        e.setFirstName(firstName);
        e.setLastName(lastName);
        e.setEmail(email);
        e.setPhone(phone);
        e.setDateOfBirth(dob);
        e.setDepartment(department);
        e.setPosition(position);
        e.setSalary(salary);                 // double auto-boxed → Double ✅
        e.setJoiningDate(joiningDate);
        e.setAddress(address);
        e.setStatus(status);
        e.setManager(manager);
        return e;
    }
}