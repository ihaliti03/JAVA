package io.distributedsystems.services;

import io.distributedsystems.models.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentService {

    List<Student> get(int page) throws SQLException;

    Student getById(String id) throws SQLException;

    String create(Student student) throws Exception;

    String update(String id, Student student) throws SQLException;

    String delete(String id) throws SQLException;
}
