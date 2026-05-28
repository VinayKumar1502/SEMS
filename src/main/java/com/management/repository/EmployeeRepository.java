package com.management.repository;

import com.management.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * EmployeeRepository - Data Access Layer for Employee entity.
 *
 * Same pattern as StudentRepository but for Employee.
 * JpaRepository<Employee, Long> gives us all basic CRUD operations.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Find employee by email.
     */
    Optional<Employee> findByEmail(String email);

    /**
     * Find all employees in a specific department.
     */
    List<Employee> findByDepartment(String department);

    /**
     * Find all employees by status.
     */
    List<Employee> findByStatus(String status);

    /**
     * Find employees by position/job title.
     */
    List<Employee> findByPosition(String position);

    /**
     * Search employees by name, email, or department.
     */
    @Query("SELECT e FROM Employee e WHERE " +
           "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(e.department) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Employee> searchEmployees(@Param("keyword") String keyword);

    /**
     * Check if email already exists.
     */
    boolean existsByEmail(String email);

    /**
     * Count employees by department.
     */
    @Query("SELECT e.department, COUNT(e) FROM Employee e GROUP BY e.department")
    List<Object[]> countByDepartment();

    /**
     * Count employees by status.
     */
    long countByStatus(String status);
}