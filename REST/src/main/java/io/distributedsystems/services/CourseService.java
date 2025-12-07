package io.distributedsystems.services;

import io.distributedsystems.models.Course;

import java.sql.SQLException;
import java.util.List;

public interface CourseService {

    List<Course> get() throws SQLException;

    Course getById(String id) throws SQLException;

    String create(Course course) throws SQLException;

    String update(String id, Course course) throws SQLException;

    String delete(String id) throws SQLException;
}
