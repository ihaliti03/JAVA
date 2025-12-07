package io.distributedsystems.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class Student {
    private String id;
    private String name;
    private String email;
    private List<Enrollment> enrollmentList;

    public Student(ResultSet rs) throws SQLException {
        this.id = (rs.getString("id"));
        this.name = rs.getString("name");
        this.email = rs.getString("email");
        this.enrollmentList = null;
    }

    public void populateInsertPs(PreparedStatement ps) throws SQLException {
        ps.setString(1, UUID.randomUUID().toString());
        ps.setString(2, this.name);
        ps.setString(3, this.email);
    }

    public void populateUpdatePs(PreparedStatement ps) throws SQLException {
        ps.setString(1, this.name);
        ps.setString(2, this.email);
        ps.setString(3, this.id);

    }

    public static void validate (Student student) throws Exception {
        if (student.getName() == null || student.getName().isEmpty()) {
            throw new Exception("Name is required");
        }
        if (student.getEmail() == null || student.getEmail().isEmpty()) {
            throw new Exception("Email is required");
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(student.getEmail()).matches()) {
            throw new Exception("Invalid email format");
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Enrollment> getEnrollmentList() {
        if (enrollmentList == null) {
            enrollmentList = new ArrayList<>();
        }
        return enrollmentList;
    }

    public void setEnrollmentList(List<Enrollment> enrollmentList) {
        this.enrollmentList = enrollmentList;
    }
}
