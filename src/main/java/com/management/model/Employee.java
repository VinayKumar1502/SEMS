package com.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Employee Entity - Maps to "employees" table in the database.
 *
 * NOTE: Lombok removed — all getters, setters, and constructors
 * are written explicitly to avoid annotation processor issues.
 */
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    @Column(name = "phone")
    private String phone;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @NotBlank(message = "Department is required")
    @Column(name = "department", nullable = false)
    private String department;

    @NotBlank(message = "Position is required")
    @Column(name = "position", nullable = false)
    private String position;

    @DecimalMin(value = "0.0", message = "Salary cannot be negative")
    @Column(name = "salary")
    private Double salary;

    @Column(name = "joining_date")
    private String joiningDate;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "status")
    private String status = "ACTIVE";

    @Column(name = "manager")
    private String manager;

    // ==========================================
    // No-args constructor — required by JPA
    // ==========================================
    public Employee() {}

    // ==========================================
    // Getters
    // ==========================================
    public Long getId()              { return id; }
    public String getFirstName()     { return firstName; }
    public String getLastName()      { return lastName; }
    public String getEmail()         { return email; }
    public String getPhone()         { return phone; }
    public String getDateOfBirth()   { return dateOfBirth; }
    public String getDepartment()    { return department; }
    public String getPosition()      { return position; }
    public Double getSalary()        { return salary; }
    public String getJoiningDate()   { return joiningDate; }
    public String getAddress()       { return address; }
    public String getStatus()        { return status; }
    public String getManager()       { return manager; }

    // ==========================================
    // Setters
    // ==========================================
    public void setId(Long id)                     { this.id = id; }
    public void setFirstName(String firstName)     { this.firstName = firstName; }
    public void setLastName(String lastName)       { this.lastName = lastName; }
    public void setEmail(String email)             { this.email = email; }
    public void setPhone(String phone)             { this.phone = phone; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setDepartment(String department)   { this.department = department; }
    public void setPosition(String position)       { this.position = position; }
    public void setSalary(Double salary)           { this.salary = salary; }
    public void setJoiningDate(String joiningDate) { this.joiningDate = joiningDate; }
    public void setAddress(String address)         { this.address = address; }
    public void setStatus(String status)           { this.status = status; }
    public void setManager(String manager)         { this.manager = manager; }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name=" + firstName + " " + lastName
                + ", department=" + department + ", position=" + position + ", status=" + status + "}";
    }
}