package io.distributedsystems.SQL;

public class StudentSQL {
    public static final String GET = "select * from students;";

    public static final String GET_PAGINATED = "SELECT * FROM students LIMIT ? OFFSET ?";

    public static final String GET_BY_ID = "select * from students where id=?;";

    public static final String CREATE = "insert into students (id, name, email) values (?, ?, ?);";

    public static final String UPDATE = "update students set name = ?, email = ? where id = ?;";

    public static final String DELETE = "delete from students where id = ?;";

}
