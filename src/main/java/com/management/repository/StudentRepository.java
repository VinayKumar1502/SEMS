package com.management.repository;

import com.management.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * StudentRepository - Data Access Layer for Student entity.
 *
 * By extending JpaRepository<Student, Long>, we get these methods FOR FREE:
 *   - save(student)          → INSERT or UPDATE
 *   - findById(id)           → SELECT by ID
 *   - findAll()              → SELECT all
 *   - deleteById(id)         → DELETE by ID
 *   - count()                → COUNT all records
 *   - existsById(id)         → Check if record exists
 *
 * We can also define custom query methods below!
 * Spring Data JPA automatically generates SQL from method names.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Find student by email address.
     * Spring generates: SELECT * FROM students WHERE email = ?
     */
    Optional<Student> findByEmail(String email);

    /**
     * Find all students in a specific course.
     * Spring generates: SELECT * FROM students WHERE course = ?
     */
    List<Student> findByCourse(String course);

    /**
     * Find all students by their status.
     * Spring generates: SELECT * FROM students WHERE status = ?
     */
    List<Student> findByStatus(String status);

    /**
     * Find students by year of study.
     */
    List<Student> findByYear(Integer year);

    /**
     * Search students by name (first or last name containing the keyword).
     * @Query allows us to write custom JPQL (Java Persistence Query Language)
     */
    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Student> searchStudents(@Param("keyword") String keyword);

    /**
     * Check if a student with this email already exists.
     */
    boolean existsByEmail(String email);

    /**
     * Count students by status.
     */
    long countByStatus(String status);
}