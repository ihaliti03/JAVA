package io.distributedsystems.servicesImpl;

import io.distributedsystems.DatabaseConnector;
import io.distributedsystems.SQL.CourseSQL;
import io.distributedsystems.models.Course;
import io.distributedsystems.services.CourseService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseServiceImpl extends CommonServiceImpl implements CourseService {

    @Override
    public List<Course> get() throws SQLException {
        List<Course> courses = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection connection = DatabaseConnector.getConnection();
        try {
            ps = connection.prepareStatement(CourseSQL.GET);
            rs = ps.executeQuery();
            while (rs.next()) {
                Course course = new Course(rs);
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new SQLException("Error while fetching courses");
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(connection);
        }
        return courses;
    }

    @Override
    public Course getById(String id) throws SQLException {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection connection = DatabaseConnector.getConnection();
        try {
            ps = connection.prepareStatement(CourseSQL.GET_BY_ID);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Course(rs);
            }
        } catch (SQLException e) {
            throw new SQLException("Error while fetching course");
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(connection);
        }
        return null;
    }

    @Override
    public String create(Course course) throws SQLException {
        PreparedStatement ps = null;
        Connection connection = DatabaseConnector.getConnection();
        try {
            ps = connection.prepareStatement(CourseSQL.CREATE);
            course.populateInsertPs(ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error while creating course");
        } finally {
            closePreparedStatement(ps);
            closeConnection(connection);
        }
        return "Successfully created course";
    }

    @Override
    public String update(String id, Course course) throws SQLException {
        PreparedStatement ps = null;
        Connection connection = DatabaseConnector.getConnection();
        try {
            ps = connection.prepareStatement(CourseSQL.UPDATE);
            course.populateUpdatePs(ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error while updating course");
        } finally {
            closePreparedStatement(ps);
            closeConnection(connection);
        }
        return "Successfully updated course";
    }

    @Override
    public String delete(String id) throws SQLException {
        PreparedStatement ps = null;
        Connection connection = DatabaseConnector.getConnection();
        try {
            ps = connection.prepareStatement(CourseSQL.DELETE);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error while deleting course");
        } finally {
            closePreparedStatement(ps);
            closeConnection(connection);
        }
        return "Successfully deleted course";
    }
}
