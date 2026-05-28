package com.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Student Entity - Maps to "students" table in the database.
 *
 * FIX: "year" is a reserved SQL keyword in H2.
 * Renamed column to "study_year" using @Column(name = "study_year")
 * The Java field name stays as "year" — only the DB column name changes.
 */
@Entity
@Table(name = "students")
public class Student {

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

    @NotBlank(message = "Course is required")
    @Column(name = "course", nullable = false)
    private String course;

    @Min(value = 1, message = "Year must be at least 1")
    @Max(value = 6, message = "Year cannot exceed 6")
    @Column(name = "study_year")   // <-- FIX: "year" is reserved in H2/SQL
    private Integer year;

    @DecimalMin(value = "0.0", message = "GPA cannot be negative")
    @DecimalMax(value = "10.0", message = "GPA cannot exceed 10.0")
    @Column(name = "gpa")
    private Double gpa;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "status")
    private String status = "ACTIVE";

    @Column(name = "enrollment_date")
    private String enrollmentDate;

    // ==========================================
    // No-args constructor — required by JPA
    // ==========================================
    public Student() {}

    // ==========================================
    // Getters
    // ==========================================
    public Long getId()                { return id; }
    public String getFirstName()       { return firstName; }
    public String getLastName()        { return lastName; }
    public String getEmail()           { return email; }
    public String getPhone()           { return phone; }
    public String getDateOfBirth()     { return dateOfBirth; }
    public String getCourse()          { return course; }
    public Integer getYear()           { return year; }
    public Double getGpa()             { return gpa; }
    public String getAddress()         { return address; }
    public String getStatus()          { return status; }
    public String getEnrollmentDate()  { return enrollmentDate; }

    // ==========================================
    // Setters
    // ==========================================
    public void setId(Long id)                           { this.id = id; }
    public void setFirstName(String firstName)           { this.firstName = firstName; }
    public void setLastName(String lastName)             { this.lastName = lastName; }
    public void setEmail(String email)                   { this.email = email; }
    public void setPhone(String phone)                   { this.phone = phone; }
    public void setDateOfBirth(String dateOfBirth)       { this.dateOfBirth = dateOfBirth; }
    public void setCourse(String course)                 { this.course = course; }
    public void setYear(Integer year)                    { this.year = year; }
    public void setGpa(Double gpa)                       { this.gpa = gpa; }
    public void setAddress(String address)               { this.address = address; }
    public void setStatus(String status)                 { this.status = status; }
    public void setEnrollmentDate(String enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name=" + firstName + " " + lastName
                + ", email=" + email + ", course=" + course + ", status=" + status + "}";
    }
}