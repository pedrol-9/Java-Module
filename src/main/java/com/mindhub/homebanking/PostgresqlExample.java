package com.mindhub.homebanking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresqlExample {
  public static void main(String[] args) throws ClassNotFoundException {
    try (final Connection connection =
                 DriverManager.getConnection("jdbc:postgresql://pg-homebanking-app-homebanking-app.d.aivencloud.com:19499/defaultdb?ssl=require&user=avnadmin&password=AVNS_dicS9MwTeFrmodlZ3li");
         final Statement statement = connection.createStatement();
         final ResultSet resultSet = statement.executeQuery("SELECT version()")) {

      while (resultSet.next()) {
        System.out.println("Version: " + resultSet.getString("version"));
      }
    } catch (SQLException e) {
      System.out.println("Connection failure.");
      e.printStackTrace();
    }
  }
}