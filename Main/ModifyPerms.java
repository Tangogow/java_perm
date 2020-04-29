package Main;

import java.sql.*;

public class ModifyPerms {
  public static int getOneOrZero(char value) {
    if (value == 'o' || value == 'O')
      return 1;
    else
      return 0;
  }

  public static boolean checkIfUserIsCreator(int fileid, int userid) {
    MySQL mysql = new MySQL();
    ResultSet result = mysql.querySelect("SELECT creator FROM files WHERE id = " + fileid);

    try {
      while (result.next()) {
        if (Integer.parseInt(result.getString(1)) == userid)
          return true;
        else
          return false;
      }
    }
    catch (Exception e) {
      System.out.println(e);
    }
    return false;
  }

  public static boolean checkIfPermExist(int permid, int fileid) {
    MySQL mysql = new MySQL();
    ResultSet result = mysql.querySelect("SELECT * FROM permissions WHERE id = " + permid + " AND file = " + fileid);

    try {
      while (result.next()) {
        if (Integer.parseInt(result.getString(1)) == 0)
          return false;
        else
          return true;
      }
    }
    catch (Exception e) {
      System.out.println(e);
    }
    return false;
  }

  public static boolean checkIfUserExist(int id) {
    MySQL mysql = new MySQL();
    ResultSet result = mysql.querySelect("SELECT * FROM users WHERE id = " + id);

    try {
      while (result.next()) {
        if (Integer.parseInt(result.getString(1)) == 0)
          return false;
        else
          return true;
      }
    }
    catch (Exception e) {
      System.out.println(e);
    }
    return false;
  }

  public static void addPerms(int nbperms, String path, String name, int userid, int fileid) {
    ViewPerms viewperm = new ViewPerms(path, name, fileid);
    int user = 0;

    try {
      user = Integer.parseInt(System.console().readLine("ID de l'utilisateur: "));
    }
    catch (Exception e) {
      System.out.println(e);
    }
    if (checkIfUserExist(user) == false) {
      System.out.println("Cet userid n'existe pas");
      modifyMenu(nbperms, path, name, userid, fileid);
    }
    int r = getOneOrZero(System.console().readLine("Lire ? [o/n] ").charAt(0));
    int w = getOneOrZero(System.console().readLine("Modifier/créer ? [o/n] ").charAt(0));
    int d = getOneOrZero(System.console().readLine("Supprimer ? [o/n] ").charAt(0));
    MySQL mysql = new MySQL();

    try {
      mysql.queryUpdate("INSERT INTO permissions(file, setted_by, for_user, r, w, d, datetime) VALUES (" + fileid + "," + userid + "," + user + "," + r + "," + w + "," + d + ",'" + Main.datetime() + "')");
    }
    catch (Exception e) {
      System.out.println(e);
    }
    System.out.println("Permission ajoutée");
    ViewPerms viewperm2 = new ViewPerms(path, name, fileid);
  }

  public static void updatePerms(int nbperms, String path, String name, int userid, int fileid) {
    ViewPerms viewperm3 = new ViewPerms(path, name, fileid);
    int permid = 0;

    try {
      permid = Integer.parseInt(System.console().readLine("ID de la permission à modifier: "));
    }
    catch (Exception e) {
      System.out.println(e);
    }
    if (checkIfPermExist(permid, fileid) == false) {
      System.out.println("Cet permission n'existe pas");
      modifyMenu(nbperms, path, name, userid, fileid);
    }
    int r = getOneOrZero(System.console().readLine("Lire ? [o/n] ").charAt(0));
    int w = getOneOrZero(System.console().readLine("Modifier/créer ? [o/n] ").charAt(0));
    int d = getOneOrZero(System.console().readLine("Supprimer ? [o/n] ").charAt(0));
    MySQL mysql = new MySQL();

    try {
      mysql.queryUpdate("UPDATE permissions SET r = " + r + ", w = " + w + ", d = " + d + ", datetime = '" + Main.datetime() + "' WHERE id = " + permid);
    }
    catch (Exception e) {
      System.out.println(e);
    }
    System.out.println("Permission modifiée");
    ViewPerms viewperm2 = new ViewPerms(path, name, fileid);
  }

  public static void deletePerms(int nbperms, String path, String name, int userid, int fileid) {
    ViewPerms viewperm4 = new ViewPerms(path, name, fileid);
    int permid = 0;

    try {
      permid = Integer.parseInt(System.console().readLine("ID de la permission à supprimer: "));
    }
    catch (Exception e) {
      System.out.println(e);
    }
    if (checkIfPermExist(permid, fileid) == false) {
      System.out.println("Cet permission n'existe pas");
      modifyMenu(nbperms, path, name, userid, fileid);
    }
    MySQL mysql = new MySQL();

    try {
      mysql.queryUpdate("DELETE FROM permissions WHERE id = " + permid);
    }
    catch (Exception e) {
      System.out.println(e);
    }
    System.out.println("Permission supprimée");
    ViewPerms viewperm6 = new ViewPerms(path, name, fileid);
  }

  public static void modifyMenu(int nbperms, String path, String name, int userid, int fileid) {
    System.out.println("1/ Ajouter une permission");
    if (nbperms != 0) {
      System.out.println("2/ Modifier une permission");
      System.out.println("3/ Supprimer une permission");
      System.out.println("4/ Retour");
    }
    else
      System.out.println("2/ Retour");

    int choice = 0;
    try {
      choice = Integer.parseInt(System.console().readLine("Choix: "));
    }
    catch (Exception e) {
      System.out.println(e);
    }

    if (nbperms != 0) {
      if (choice == 1)
        addPerms(nbperms, path, name, userid, fileid);
      else if (choice == 2)
        updatePerms(nbperms, path, name, userid, fileid);
      else if (choice == 3)
        deletePerms(nbperms, path, name, userid, fileid);
      else if (choice == 4)
        Main.menu();
      else {
        System.out.println("Erreur: Choix inconnu");
        modifyMenu(nbperms, path, name, userid, fileid);
      }
    }
    else {
      if (choice == 1)
        addPerms(nbperms, path, name, userid, fileid);
      else if (choice == 2)
        Main.menu();
      else {
        System.out.println("Erreur: Choix inconnu");
        modifyMenu(nbperms, path, name, userid, fileid);
      }
    }
  }

  public static int countPerms(String path, String name, int idfile) {
    MySQL mysql = new MySQL();
    ResultSet result = mysql.querySelect("SELECT COUNT(*) FROM permissions WHERE file = " + idfile);

    try {
      while (result.next()) {
        return Integer.parseInt(result.getString(1));
      }
    }
    catch (Exception e) {
      System.out.println(e);
    }
    return -1;
  }

  public ModifyPerms(String path, String name, int fileid, int userid) {
    if (checkIfUserIsCreator(fileid, userid) == false) {
      System.out.println("Ce fichier/dossier ne vous appartient pas. Vous ne pouvez le modifier.");
      return;
    }
    int nbperms = countPerms(path, name, fileid);
    modifyMenu(nbperms, path, name, userid, fileid);
  }
}
