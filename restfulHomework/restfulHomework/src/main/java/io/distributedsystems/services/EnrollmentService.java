package io.distributedsystems.services;

import io.distributedsystems.models.Course;
import io.distributedsystems.models.Student;

import java.sql.SQLException;
import java.util.List;

public interface EnrollmentService {

    String create(String studentId, String courseId) throws SQLException;

    String delete(String studentId, String courseId) throws SQLException;

    List<Course> getStudentsEnrolledCourses(String studentId) throws SQLException;

    List<Student> getCoursesEnrolledStudents(String courseId) throws SQLException;
}
