package com.billyyccc;

import com.billyyccc.mssqlclient.MSSQLConnectOptions;
import com.billyyccc.mssqlclient.MSSQLPool;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlClient;

import java.time.LocalDate;

import static com.billyyccc.Scripts.*;

public class Examples {
  private static SqlClient client;
  private static MSSQLConnectOptions connectOptions = loadConnectOptions();
  private static PoolOptions poolOptions = new PoolOptions();

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    client = MSSQLPool.pool(vertx, connectOptions, poolOptions);

    // choose what you want
    queryExample();
//        preparedQueryExample();
  }

  /**
   * Examples to use SQL Batch(i.e. Simple text query).
   */
  private static void queryExample() {
    // First let's create a table
    client.query(CREATE_TABLE, createTable -> {
      if (createTable.succeeded()) {
        System.out.println("Create table success!");

        // let's insert the records now
        Future<RowSet> insertFirstRecord = executeQuery(INSERT_FIRST_RECORD);
        Future<RowSet> insertSecondRecord = executeQuery(INSERT_SECOND_RECORD);
        Future<RowSet> insertThirdRecord = executeQuery(INSERT_THIRD_RECORD);
        CompositeFuture.all(insertFirstRecord, insertSecondRecord, insertThirdRecord)
          .setHandler(insertRecords -> {
            if (insertRecords.succeeded()) {
              System.out.println("Insert records success!");

              // let's see what's in the table
              client.query(GET_ALL_TODOS, getAllRecords -> {
                if (getAllRecords.succeeded()) {
                  System.out.println("Get all records.");
                  RowSet rows = getAllRecords.result();
                  handleAllRows(rows);

                  // drop the table
                  client.query(DROP_TABLE, dropTable -> {
                    if (dropTable.succeeded()) {
                      System.out.println("Drop table success");

                      // exit
                      System.exit(0);
                    } else {
                      System.out.println("Failed to drop table: " + dropTable.cause().getMessage());
                    }
                  });
                } else {
                  System.out.println("Failed to get all records: " + getAllRecords.cause().getMessage());
                }
              });
            } else {
              System.out.println("Failed to insert records: " + insertRecords.cause().getMessage());
            }
          });
      } else {
        System.out.println("Failed to create table: " + createTable.cause().getMessage());
      }
    });
  }

  /**
   * Examples to use prepared queries.
   */
  private static void preparedQueryExample() {
    // First let's create a table
    client.preparedQuery(CREATE_TABLE, createTable -> {
      if (createTable.succeeded()) {
        System.out.println("Create table success!");

        // let's insert the records now
        Future<RowSet> insertFirstRecord = executePreparedQuery(INSERT_FIRST_RECORD);
        Future<RowSet> insertSecondRecord = executePreparedQuery(INSERT_SECOND_RECORD);
        Future<RowSet> insertThirdRecord = executePreparedQuery(INSERT_THIRD_RECORD);
        CompositeFuture.all(insertFirstRecord, insertSecondRecord, insertThirdRecord)
          .setHandler(insertRecords -> {
            if (insertRecords.succeeded()) {
              System.out.println("Insert records success!");

              // let's see what's in the table
              client.preparedQuery(GET_ALL_TODOS, getAllRecords -> {
                if (getAllRecords.succeeded()) {
                  System.out.println("Get all records.");
                  RowSet rows = getAllRecords.result();
                  handleAllRows(rows);

                  // drop the table
                  client.preparedQuery(DROP_TABLE, dropTable -> {
                    if (dropTable.succeeded()) {
                      System.out.println("Drop table success");

                      // exit
                      System.exit(0);
                    } else {
                      System.out.println("Failed to drop table: " + dropTable.cause().getMessage());
                    }
                  });
                } else {
                  System.out.println("Failed to get all records: " + getAllRecords.cause().getMessage());
                }
              });
            } else {
              System.out.println("Failed to insert records: " + insertRecords.cause().getMessage());
            }
          });
      } else {
        System.out.println("Failed to create table: " + createTable.cause().getMessage());
      }
    });
  }

  private static MSSQLConnectOptions loadConnectOptions() {
    return new MSSQLConnectOptions()
      .setHost("localhost")
      .setPort(1433)
      .setUser("sa")
      .setPassword("yourStrong(!)Password");
  }

  private static Future<RowSet> executeQuery(String sql) {
    Promise<RowSet> promise = Promise.promise();
    client.query(sql, promise);
    return promise.future();
  }

  private static Future<RowSet> executePreparedQuery(String sql) {
    Promise<RowSet> promise = Promise.promise();
    client.preparedQuery(sql, promise);
    return promise.future();
  }

  private static void handleAllRows(RowSet rows) {
    System.out.println(rows.columnsNames());
    for (Row row : rows) {
      int id = row.getInteger("id");
      String todoContent = row.getString("todo_content");
      int priority = row.getInteger("priority");
      LocalDate createdDate = row.getLocalDate("created_date");
      LocalDate deadline = row.getLocalDate("deadline");
      boolean isDone = row.getBoolean("is_done");
      System.out.println("TODO id: " + id +
        ", content: " + todoContent +
        ", priority: " + priority +
        ", createdDate: " + createdDate +
        ", deadline: " + deadline +
        ", isDone: " + isDone);
    }
  }
}
