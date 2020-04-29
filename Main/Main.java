package Main;

import Lib.Keyboard;
import java.sql.*;
import java.io.*;
import BCrypt.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

public class Main {
  private static final String HOME = "../";
  public static int fileid = 0;
  private static int userid = 0;
  private static String doctype = null;
  private static String extension = null;

  public static String datetime() {
   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
   LocalDateTime now = LocalDateTime.now();

   return dtf.format(now);
  }

  public static void modifyPerms() {
    System.out.println("** Modification permissions **");
    String path = System.console().readLine("Rentrer le chemin du dossier/fichier (sans le nom avec slash):\n");
    String name = System.console().readLine("Rentrer le nom du dossier/fichier à modifier:\n");

    if (path.equals("quit") || path.equals("exit") || name.equals("quit") || name.equals("exit"))
      menu();
    if (checkDir(path, name) == 0)
      modifyPerms();
    ModifyPerms modifyperm = new ModifyPerms(path, name, fileid, userid);
    menu();
  }

  public static int checkDir(String path, String name) {
    MySQL mysql = new MySQL();
    ResultSet result = mysql.querySelect("SELECT id, doctype, extension FROM files WHERE path = '" + path + "' AND name = '" + name + "'");

    try {
      while (result.next()) {
        fileid = Integer.parseInt(result.getString(1));
        doctype = result.getString(2);
        extension = result.getString(3);
      }
    }
    catch (Exception e) {
      System.out.println(e);
    }

    mysql.closeMySQL();
    if (fileid == 0) {
      System.out.println(">Pas d'occurence trouvé.");
    }
    else {
      if (doctype.equals("folder"))
        System.out.println(">Dossier trouvé");
      else
        System.out.println(">Fichier trouvé");
    }
    return fileid;
  }

  public static void viewPerms() {
    System.out.println("** Vue permissions **");
    String path = System.console().readLine("Rentrer le chemin du dossier/fichier (sans le nom avec slash):\n");
    String name = System.console().readLine("Rentrer le nom du dossier à voir:\n");

    if (path.equals("quit") || path.equals("exit") || name.equals("quit") || name.equals("exit"))
      menu();
    if (checkDir(path, name) == 0)
      viewPerms();
    ViewPerms viewperm = new ViewPerms(path, name, fileid);
    menu();
  }

  public static void menu() {
    System.out.println("** Menu principal **");
    System.out.println("1/ Voir permissions");
    System.out.println("2/ Modifier permissions");
    System.out.println("3/ Quitter");
    int choice = 0;

    try {
      choice = Integer.parseInt(System.console().readLine("Choix: "));
    }
    catch (Exception e) {
      System.out.println(e);
    }

    if (choice == 1)
      viewPerms();
    else if (choice == 2)
      modifyPerms();
    else if (choice == 3)
      System.exit(0);
    else {
      System.out.println("Erreur: Choix inconnu");
      menu();
    }
  }

  public static void checkStatus(String status) {
    if (status.equals("inactive")) {
      System.out.println(">Votre compte n'est pas activé.");
      System.exit(0);
    }
    else if (status.equals("banned")) {
      System.out.println(">Votre compte est banni.");
      System.exit(0);
    }
  }

  public static void checkPwd(String dbpwd) {
    String pwd = null;
    int counter = 0;

    pwd = String.valueOf(System.console().readPassword("Mot de passe: "));
    while (BCrypt.checkpw(pwd, dbpwd) == false) {
      if (counter == 2) {
        System.out.println(">Maximum de tentative atteint. Fin.");
        System.exit(0);
      }
      System.out.println("Mot de passe incorrect");
      pwd = String.valueOf(System.console().readPassword("Mot de passe: "));
      counter++;
    }
  }

  public static boolean checkEmailExist(String email) {
    MySQL mysql = new MySQL();
    ResultSet result = mysql.querySelect("SELECT id FROM users WHERE email = '" + email + "'");
    int id = 0;

    try {
      while (result.next()) {
        id = Integer.parseInt(result.getString(1));
      }
    }
    catch (Exception e) {}

    if (id == 0)
      return false;
    else
      return true;
  }

  public static boolean regexEmail(String email) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    Pattern pat = Pattern.compile(emailRegex);

    if (email == null || email.isEmpty())
      return false;
    return pat.matcher(email).matches();
  }

  public static String checkEmail(String email) {
    email = System.console().readLine("Email: ");

    if (email.equals("quit") || email.equals("exit"))
      System.exit(0);
    while (true) {
      if (!regexEmail(email))
        System.out.println("Email invalide");
      else if (checkEmailExist(email) == false)
        System.out.println("Cette email n'existe pas.");
      else
        break;
      email = System.console().readLine("Email: ");
    }
    return email;
  }

  public static void main(String[] args) {
    Console console = System.console();
    String email = null;
    String firstname = null;
    String lastname = null;
    String dbpwd = null;
    String status = null;

    System.out.println("** L'oranger - Gestionnaire des permissions **");
    email = checkEmail(email);

    MySQL mysql = new MySQL();
    ResultSet result = mysql.querySelect("SELECT id, firstname, lastname, password, status FROM users WHERE email = '" + email + "'");

    try {
      while (result.next()) {
        userid = Integer.parseInt(result.getString(1));
        firstname = result.getString(2);
        lastname = result.getString(3);
        dbpwd = result.getString(4);
        status = result.getString(5);
      }
    }
    catch (Exception e) {
      System.out.println(e);
    }
    mysql.closeMySQL();

    checkPwd(dbpwd);
    checkStatus(status);
    System.out.println("Bienvenue " + firstname + " " + lastname);
    menu();
  }
}
