package io.distributedsystems.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Course {
    private String id;
    private String title;
    private String description;
    private String instructor;
    private List<Enrollment> enrollments;

    public Course(ResultSet rs) throws SQLException {
        this.id = (rs.getString("id"));
        this.title = rs.getString("title");
        this.description = rs.getString("description");
        this.instructor = rs.getString("instructor");
        this.enrollments = null;
    }

    public void populateInsertPs(PreparedStatement ps) throws SQLException {
        ps.setString(1, UUID.randomUUID().toString());
        ps.setString(2, this.title);
        ps.setString(3, this.description);
        ps.setString(4, this.instructor);
    }

    public void populateUpdatePs(PreparedStatement ps) throws SQLException {
        ps.setString(1, this.title);
        ps.setString(2, this.description);
        ps.setString(3, this.instructor);
        ps.setString(4, this.id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public List<Enrollment> getEnrollments() {
        if (enrollments == null) {
            enrollments = new ArrayList<>();
        }
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }
}
