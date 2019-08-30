package com.billyyccc;

/**
 * SQL scripts used for examples
 */
public class Scripts {
  public static final String CREATE_TABLE = "CREATE TABLE todo_info\n" +
    "(\n" +
    "    id           INTEGER,\n" +
    "    todo_content VARCHAR(100),\n" +
    "    priority     INTEGER,\n" +
    "    created_date  DATE DEFAULT CURRENT_TIMESTAMP,\n" +
    "    deadline     DATE,\n" +
    "    is_done      BIT,\n" +
    "    PRIMARY KEY (id)\n" +
    ");";
  public static final String INSERT_FIRST_RECORD = "INSERT INTO todo_info(id, todo_content, priority, deadline, is_done)\n" +
    "VALUES (1, 'Go swimming', 2, '2019-09-01', 0);";
  public static final String INSERT_SECOND_RECORD = "INSERT INTO todo_info(id, todo_content, priority, deadline, is_done)\n" +
    "VALUES (2, 'Visit an old friend', 5, '2019-10-01', 0);";
  public static final String INSERT_THIRD_RECORD = "INSERT INTO todo_info(id, todo_content, priority, deadline, is_done)\n" +
    "VALUES (3, 'Read a book', 3, '2020-01-01', 0);";

  public static final String GET_ALL_TODOS = "SELECT * FROM todo_info;";

  public static final String GET_HIGH_PRIORITY_TODO = "SELECT * FROM todo_info WHERE priority > 3";

  public static final String DROP_TABLE = "DROP TABLE todo_info";
}
