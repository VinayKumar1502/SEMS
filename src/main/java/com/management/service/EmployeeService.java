package com.management.service;

import com.management.model.Employee;
import com.management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * EmployeeService - Business Logic Layer for Employee operations.
 *
 * Follows same structure as StudentService.
 * All business rules and validations are handled here.
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // ==========================================
    // CREATE
    // ==========================================

    /**
     * Create a new employee record.
     * Validates email uniqueness before saving.
     */
    public Employee createEmployee(Employee employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new RuntimeException("An employee with email '" + employee.getEmail() + "' already exists!");
        }
        return employeeRepository.save(employee);
    }

    // ==========================================
    // READ
    // ==========================================

    /**
     * Get all employees.
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Get employee by ID.
     */
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
    }

    /**
     * Search employees by keyword.
     */
    public List<Employee> searchEmployees(String keyword) {
        return employeeRepository.searchEmployees(keyword);
    }

    /**
     * Get all employees in a department.
     */
    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }

    /**
     * Get all employees by status.
     */
    public List<Employee> getEmployeesByStatus(String status) {
        return employeeRepository.findByStatus(status);
    }

    // ==========================================
    // UPDATE
    // ==========================================

    /**
     * Update employee information.
     */
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existing = getEmployeeById(id);

        // Check email uniqueness only if email changed
        if (!existing.getEmail().equals(updatedEmployee.getEmail())
                && employeeRepository.existsByEmail(updatedEmployee.getEmail())) {
            throw new RuntimeException("Email '" + updatedEmployee.getEmail() + "' is already in use!");
        }

        // Update all fields
        existing.setFirstName(updatedEmployee.getFirstName());
        existing.setLastName(updatedEmployee.getLastName());
        existing.setEmail(updatedEmployee.getEmail());
        existing.setPhone(updatedEmployee.getPhone());
        existing.setDateOfBirth(updatedEmployee.getDateOfBirth());
        existing.setDepartment(updatedEmployee.getDepartment());
        existing.setPosition(updatedEmployee.getPosition());
        existing.setSalary(updatedEmployee.getSalary());
        existing.setJoiningDate(updatedEmployee.getJoiningDate());
        existing.setAddress(updatedEmployee.getAddress());
        existing.setStatus(updatedEmployee.getStatus());
        existing.setManager(updatedEmployee.getManager());

        return employeeRepository.save(existing);
    }

    // ==========================================
    // DELETE
    // ==========================================

    /**
     * Delete an employee by ID.
     */
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found with ID: " + id);
        }
        employeeRepository.deleteById(id);
    }

    // ==========================================
    // Statistics
    // ==========================================

    /**
     * Get employee statistics for the dashboard.
     */
    public EmployeeStats getStats() {
        long total = employeeRepository.count();
        long active = employeeRepository.countByStatus("ACTIVE");
        long onLeave = employeeRepository.countByStatus("ON_LEAVE");
        long inactive = employeeRepository.countByStatus("INACTIVE");
        return new EmployeeStats(total, active, onLeave, inactive);
    }

    public record EmployeeStats(long total, long active, long onLeave, long inactive) {}
}