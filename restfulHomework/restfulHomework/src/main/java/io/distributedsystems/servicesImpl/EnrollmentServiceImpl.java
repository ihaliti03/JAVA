package io.distributedsystems.servicesImpl;

import io.distributedsystems.DatabaseConnector;
import io.distributedsystems.SQL.EnrollmentSQL;
import io.distributedsystems.models.Course;
import io.distributedsystems.models.Student;
import io.distributedsystems.services.EnrollmentService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EnrollmentServiceImpl extends CommonServiceImpl implements EnrollmentService {

    @Override
    public String create(String studentId, String courseId) throws SQLException {
        PreparedStatement ps = null;
        Connection connection = DatabaseConnector.getConnection();
        try {
            ps = connection.prepareStatement(EnrollmentSQL.CREATE);
            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, studentId);
            ps.setString(3, courseId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error while trying to enroll student because either student or course id is incorrect");
        } finally {
            closePreparedStatement(ps);
            closeConnection(connection);
        }
        return "Successfully enrolled";
    }

    @Override
    public String delete(String studentId, String courseId) throws SQLException {
        PreparedStatement ps = null;
        Connection connection = null;
        try {
            connection = DatabaseConnector.getConnection();
            ps = connection.prepareStatement(EnrollmentSQL.DELETE);
            ps.setString(1, studentId);
            ps.setString(2, courseId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error while trying to delete enrollment because student or course id is incorrect");
        } finally {
            closePreparedStatement(ps);
            closeConnection(connection);
        }
        return "Successfully deleted";
    }

    @Override
    public List<Course> getStudentsEnrolledCourses(String studentId) throws SQLException {
        List<Course> courses = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection connection = null;
        try{
            connection = DatabaseConnector.getConnection();
            ps = connection.prepareStatement(EnrollmentSQL.GET_STUDENTS_ENROLLED_COURSES);
            ps.setString(1, studentId);
            rs = ps.executeQuery();
            while(rs.next()){
                courses.add(new Course(rs));
            }
        } catch (SQLException e) {
            throw new SQLException("Error while trying to get the student's courses");
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(connection);
        }
        return courses;
    }

    @Override
    public List<Student> getCoursesEnrolledStudents(String courseId) throws SQLException {
        List<Student> students = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection connection = null;
        try{
            connection = DatabaseConnector.getConnection();
            ps = connection.prepareStatement(EnrollmentSQL.GET_COURSES_ENROLLED_STUDENTS);
            ps.setString(1, courseId);
            rs = ps.executeQuery();
            while(rs.next()){
                students.add(new Student(rs));
            }
        } catch (SQLException e) {
            throw new SQLException("Error while trying to get the courses enrolled students");
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(connection);
        }
        return students;
    }
}
