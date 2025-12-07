package io.distributedsystems.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Enrollment {
    private String id;
    private String studentId;
    private String courseId;

    public Enrollment(ResultSet rs) throws SQLException {
        this.id = rs.getString("id");
        this.studentId = rs.getString("student_id");
        this.courseId = rs.getString("course_id");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
