package Main;

import java.sql.*;
import java.util.*;
import java.io.*;

public class MySQL {
  Connection connection = null;
  Statement stmt = null;
  ResultSet resultSelect = null;
  int resultUpdate = 0;
  private static String username = null;
  private static String password = null;
  private static String database = null;
  private static String hostname = null;;
  private static String port = null;;
  private static String options = null;

  public int queryUpdate(String query) {
    try {
      this.resultUpdate = stmt.executeUpdate(query);
    }
    catch (Exception e) {
      System.out.println(e);
    }

    return this.resultUpdate;
  }

  public ResultSet querySelect(String query) {
    try {
      this.resultSelect = stmt.executeQuery(query);
    }
    catch (Exception e) {
      System.out.println(e);
    }

    return this.resultSelect;
  }

  public void closeMySQL() {
    if (this.connection != null) {
      try {
        this.resultSelect.close();
        this.stmt.close();
        this.connection.close();
      }
      catch (Exception e) {}
    }
  }

  public static void readIni() {
    try {
      Properties p = new Properties();

      p.load(new FileInputStream("config.ini"));
      username = p.getProperty("dbUser");
      password = p.getProperty("dbPwd");
      database = p.getProperty("dbName");
      hostname = p.getProperty("dbHost");
      port = p.getProperty("dbPort");
      options = p.getProperty("dbOptions");
    }
    catch (Exception e) {
      System.out.println(e);
    }
  }

  public MySQL() {
    String url = null;
    readIni();
    try {
      url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + options;
      Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
      connection = DriverManager.getConnection (url, username, password);
      stmt = connection.createStatement();
    }
    catch (SQLException e) {
      System.err.println ("Cannot connect to database server:");
      System.out.println(e.getMessage());
    }
    catch (ClassNotFoundException e) {
      System.err.println ("Cannot connect to database server:");
      System.out.println(e.getMessage());
    }
    catch (InstantiationException e) {
      System.err.println ("Cannot connect to database server:");
      System.out.println(e.getMessage());
    }
    catch (IllegalAccessException e) {
      System.err.println ("Cannot connect to database server:");
      System.out.println(e.getMessage());
    }
    finally {
      closeMySQL();
    }
  }
}
