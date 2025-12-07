package io.distributedsystems.SQL;

public class CourseSQL {
    public static final String GET = "select * from courses;";

    public static final String GET_BY_ID = "select * from courses where id=?;";

    public static final String CREATE = "insert into courses (id, title, description, instructor) values (?, ?, ?, ?);";

    public static final String UPDATE = "update courses set title = ?, description = ?, instructor = ? where id = ?;";

    public static final String DELETE = "delete from courses where id=?;";


}
