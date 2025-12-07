package io.distributedsystems.SQL;

public class EnrollmentSQL {

    public static final String CREATE = "insert into enrollments (id, student_id, course_id) values (?, ?, ?);";

    public static final String DELETE = "delete from enrollments where student_id = ? and course_id = ?;";

    public static final String GET_STUDENTS_ENROLLED_COURSES = "SELECT c.id, c.title\n" +
            "            FROM courses c\n" +
            "            JOIN enrollments e ON c.id = e.course_id\n" +
            "            WHERE e.student_id = ?";

    public static final String GET_COURSES_ENROLLED_STUDENTS = "SELECT s.id, s.name\n" +
            "            FROM students s\n" +
            "            JOIN enrollments e ON s.id = e.student_id\n" +
            "            WHERE e.student_id = ?";
}
