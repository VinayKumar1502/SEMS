package com.management.controller;

import com.management.model.Student;
import com.management.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * StudentController - REST API Layer for Student operations.
 *
 * @RestController = @Controller + @ResponseBody
 *   Means all methods return JSON/data directly (not HTML views)
 *
 * @RequestMapping("/api/students")
 *   All URLs in this class start with /api/students
 *
 * @CrossOrigin - Allows requests from any origin (needed for frontend to call this API)
 *
 * HTTP Methods used:
 *   GET    → Read data
 *   POST   → Create new data
 *   PUT    → Update existing data
 *   DELETE → Delete data
 */
@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // ==========================================
    // GET /api/students → Get all students
    // ==========================================
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students); // HTTP 200 OK
    }

    // ==========================================
    // GET /api/students/{id} → Get student by ID
    // ==========================================
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        try {
            Student student = studentService.getStudentById(id);
            return ResponseEntity.ok(student);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorResponse(e.getMessage())); // HTTP 404
        }
    }

    // ==========================================
    // GET /api/students/search?keyword=xyz → Search
    // ==========================================
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchStudents(@RequestParam String keyword) {
        List<Student> students = studentService.searchStudents(keyword);
        return ResponseEntity.ok(students);
    }

    // ==========================================
    // GET /api/students/stats → Dashboard stats
    // ==========================================
    @GetMapping("/stats")
    public ResponseEntity<StudentService.StudentStats> getStats() {
        return ResponseEntity.ok(studentService.getStats());
    }

    // ==========================================
    // POST /api/students → Create new student
    // ==========================================
    @PostMapping
    public ResponseEntity<?> createStudent(@Valid @RequestBody Student student) {
        try {
            Student savedStudent = studentService.createStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent); // HTTP 201 Created
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(errorResponse(e.getMessage())); // HTTP 409 Conflict
        }
    }

    // ==========================================
    // PUT /api/students/{id} → Update student
    // ==========================================
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id,
                                           @Valid @RequestBody Student student) {
        try {
            Student updatedStudent = studentService.updateStudent(id, student);
            return ResponseEntity.ok(updatedStudent);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errorResponse(e.getMessage()));
        }
    }

    // ==========================================
    // DELETE /api/students/{id} → Delete student
    // ==========================================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok(successResponse("Student deleted successfully!"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorResponse(e.getMessage()));
        }
    }

    // ==========================================
    // Helper Methods to build response objects
    // ==========================================

    private Map<String, String> errorResponse(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", message);
        return response;
    }

    private Map<String, String> successResponse(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", message);
        return response;
    }
}