package io.distributedsystems.servicesImpl;

import io.distributedsystems.DatabaseConnector;
import io.distributedsystems.SQL.StudentSQL;
import io.distributedsystems.models.Student;
import io.distributedsystems.services.StudentService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl extends CommonServiceImpl implements StudentService {

    @Override
    public List<Student> get(int page) throws SQLException {
        List<Student> students = new ArrayList<>();
        int offset = (page - 1) * 10;
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection connection = null;
        try {
            connection = DatabaseConnector.getConnection();
            ps = connection.prepareStatement(StudentSQL.GET_PAGINATED);
            ps.setInt(1, 10);
            ps.setInt(2, offset);
            rs = ps.executeQuery();
            while (rs.next()) {
                students.add(new Student(rs));
            }
        } catch (SQLException e) {
            throw new SQLException("Error while fetching students");
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(connection);
        }
        return students;
    }

    @Override
    public Student getById(String id) throws SQLException {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection connection = null;
        try {
            connection = DatabaseConnector.getConnection();
            ps = connection.prepareStatement(StudentSQL.GET_BY_ID);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Student(rs);
            }
        } catch (SQLException e) {
            throw new SQLException("Error while fetching student");
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(connection);
        }
        return null;
    }

    @Override
    public String create(Student student) throws Exception {
        Student.validate(student);
        PreparedStatement ps = null;
        Connection connection = null;
        try {
            connection = DatabaseConnector.getConnection();
            ps = connection.prepareStatement(StudentSQL.CREATE);
            student.populateInsertPs(ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error while creating student");
        } finally {
            closePreparedStatement(ps);
            closeConnection(connection);
        }
        return "Successfully created student";
    }

    @Override
    public String update(String id, Student student) throws SQLException {
        PreparedStatement ps = null;
        Connection connection = null;
        try {
            connection = DatabaseConnector.getConnection();
            ps = connection.prepareStatement(StudentSQL.UPDATE);
            student.populateUpdatePs(ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error while updating student");
        } finally {
            closePreparedStatement(ps);
            closeConnection(connection);
        }
        return "Successfully updated student";
    }

    @Override
    public String delete(String id) throws SQLException {
        PreparedStatement ps = null;
        Connection connection = null;
        try {
            connection = DatabaseConnector.getConnection();
            ps = connection.prepareStatement(StudentSQL.DELETE);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error while deleting student");
        } finally {
            closePreparedStatement(ps);
            closeConnection(connection);
        }
        return "Successfully deleted student";
    }
}
