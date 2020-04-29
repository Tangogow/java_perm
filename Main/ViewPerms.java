package Main;

import java.sql.*;

public class ViewPerms {
  public static String getYesOrNo(int value) {
    if (value == 1)
      return "Oui";
    else
      return "Non";
  }

  public static String getSetterLastName(int idsetter) {
    MySQL mysql = new MySQL();
    ResultSet result = mysql.querySelect("SELECT lastname FROM users WHERE id = " + idsetter);

    try {
      while (result.next()) {
        return result.getString(1);
      }
    }
    catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  public static String getSetterFirstName(int idsetter) {
    MySQL mysql = new MySQL();
    ResultSet result = mysql.querySelect("SELECT firstname FROM users WHERE id = " + idsetter);

    try {
      while (result.next()) {
        return result.getString(1);
      }
    }
    catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  public static String getUserFirstName(int iduser) {
    MySQL mysql = new MySQL();
    ResultSet result = mysql.querySelect("SELECT firstname FROM users WHERE id = " + iduser);

    try {
      while (result.next()) {
        return result.getString(1);
      }
    }
    catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  public static String getUserLastName(int iduser) {
    MySQL mysql = new MySQL();
    ResultSet result = mysql.querySelect("SELECT lastname FROM users WHERE id = " + iduser);

    try {
      while (result.next()) {
        return result.getString(1);
      }
    }
    catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  public static String getExtension(int idfile) {
    MySQL mysql = new MySQL();
    ResultSet result = mysql.querySelect("SELECT extension FROM files WHERE id = " + idfile);

    try {
      while (result.next()) {
        if (result.getString(1) == null)
          return "";
        else
          return "." + result.getString(1);
      }
    }
    catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  public static String getDoctype(int idfile) {
    MySQL mysql = new MySQL();
    ResultSet result = mysql.querySelect("SELECT doctype FROM files WHERE id = " + idfile);

    try {
      while (result.next()) {
        return result.getString(1);
      }
    }
    catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  public ViewPerms(String path, String name, int idfile) {
    int idperm = 0;
    int idsetter = 0;
    int iduser = 0;
    int r = 0;
    int w = 0;
    int d = 0;
    String date = null;
    MySQL mysql = new MySQL();
    ResultSet result = mysql.querySelect("SELECT * FROM permissions WHERE file = " + idfile);
    String buffer = null;

    try {
      System.out.println("+----+---------------+--------+--------------+-------------+--------+--------+--------+-----------------------+");
      System.out.println("| id |    Fichier    |  Type  |   Mise par   |     Pour    |  Lire  | Ecrire | Suppr  |          Date         |");
      System.out.println("+----+---------------+--------+--------------+-------------+--------+--------+--------+-----------------------+");
      while (result.next()) {
        idperm = Integer.parseInt(result.getString(1));
        idsetter = Integer.parseInt(result.getString(3));
        iduser = Integer.parseInt(result.getString(4));
        r = Integer.parseInt(result.getString(5));
        w = Integer.parseInt(result.getString(6));
        d = Integer.parseInt(result.getString(7));
        date = result.getString(8);
        System.out.println("| " + idperm + "  | " + path + name + getExtension(idfile) + " | " + getDoctype(idfile) + " | " + getSetterFirstName(idsetter) + " " + getSetterLastName(idsetter) + "  | " + getUserFirstName(iduser) + " " + getUserLastName(iduser) + " |  " + getYesOrNo(r) + "   |  " + getYesOrNo(w) + "   |  " + getYesOrNo(d) + "   | " + date + " |");
      }
      System.out.println("+----+---------------+--------+--------------+-------------+--------+--------+--------+-----------------------+");
    }
    catch (Exception e) {
      System.out.println(e);
    }
  }
}
