package com.management.service;

import com.management.model.Student;
import com.management.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * StudentService - Business Logic Layer for Student operations.
 *
 * @Service annotation marks this as a Spring Service component.
 *
 * The Service layer sits between Controller and Repository:
 *   Controller → Service → Repository → Database
 *
 * Business logic (validation, rules, calculations) goes HERE,
 * not in the Controller or Repository.
 */
@Service
public class StudentService {

    /**
     * @Autowired - Spring automatically injects the StudentRepository bean.
     * We don't need to create it manually using 'new'.
     */
    @Autowired
    private StudentRepository studentRepository;

    // ==========================================
    // CREATE Operations
    // ==========================================

    /**
     * Add a new student to the database.
     * Checks for duplicate email before saving.
     *
     * @param student - Student object to save
     * @return saved Student with generated ID
     * @throws RuntimeException if email already exists
     */
    public Student createStudent(Student student) {
        // Business Rule: Email must be unique
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new RuntimeException("A student with email '" + student.getEmail() + "' already exists!");
        }
        return studentRepository.save(student);
    }

    // ==========================================
    // READ Operations
    // ==========================================

    /**
     * Get all students from the database.
     * @return List of all students
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Get a single student by their ID.
     * @param id - Student ID
     * @return Student if found
     * @throws RuntimeException if student not found
     */
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
    }

    /**
     * Search students by keyword (name or email).
     */
    public List<Student> searchStudents(String keyword) {
        return studentRepository.searchStudents(keyword);
    }

    /**
     * Get all students in a specific course.
     */
    public List<Student> getStudentsByCourse(String course) {
        return studentRepository.findByCourse(course);
    }

    /**
     * Get all students by status.
     */
    public List<Student> getStudentsByStatus(String status) {
        return studentRepository.findByStatus(status);
    }

    // ==========================================
    // UPDATE Operations
    // ==========================================

    /**
     * Update an existing student's information.
     *
     * @param id - ID of the student to update
     * @param updatedStudent - New student data
     * @return Updated Student object
     */
    public Student updateStudent(Long id, Student updatedStudent) {
        // First check if the student exists
        Student existingStudent = getStudentById(id);

        // Check email uniqueness only if email is changing
        if (!existingStudent.getEmail().equals(updatedStudent.getEmail())
                && studentRepository.existsByEmail(updatedStudent.getEmail())) {
            throw new RuntimeException("Email '" + updatedStudent.getEmail() + "' is already in use!");
        }

        // Update all fields
        existingStudent.setFirstName(updatedStudent.getFirstName());
        existingStudent.setLastName(updatedStudent.getLastName());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setPhone(updatedStudent.getPhone());
        existingStudent.setDateOfBirth(updatedStudent.getDateOfBirth());
        existingStudent.setCourse(updatedStudent.getCourse());
        existingStudent.setYear(updatedStudent.getYear());
        existingStudent.setGpa(updatedStudent.getGpa());
        existingStudent.setAddress(updatedStudent.getAddress());
        existingStudent.setStatus(updatedStudent.getStatus());
        existingStudent.setEnrollmentDate(updatedStudent.getEnrollmentDate());

        return studentRepository.save(existingStudent);
    }

    // ==========================================
    // DELETE Operations
    // ==========================================

    /**
     * Delete a student by their ID.
     * @param id - Student ID to delete
     */
    public void deleteStudent(Long id) {
        // Verify student exists before deleting
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }

    // ==========================================
    // Statistics / Summary
    // ==========================================

    /**
     * Get summary statistics for the dashboard.
     */
    public StudentStats getStats() {
        long total = studentRepository.count();
        long active = studentRepository.countByStatus("ACTIVE");
        long inactive = studentRepository.countByStatus("INACTIVE");
        long graduated = studentRepository.countByStatus("GRADUATED");
        return new StudentStats(total, active, inactive, graduated);
    }

    /**
     * Inner class to hold statistics data.
     * Simple record to return multiple values at once.
     */
    public record StudentStats(long total, long active, long inactive, long graduated) {}
}