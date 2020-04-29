package Lib;

import java.io.*;

/**
 * Le principal but de la classe Clavier est de proposer des
 * entrées clavier sans exception. On propose ainsi
 * une opération élémentaire de lecture sur l'entrée standard. Cette
 * classe permet d'éviter de rentrer dans les détails complexes des
 * entrées-sorties.
 */

public class Keyboard {
  /**
   * Cette méthode lit un entier dans une chaéne de caractéres.
   * Elle s'occupe des éventuelles exceptions. Si une exception
   * (NumberFormatException) est attrapée, la méthode renvoie l'entier
   * nul et affiche un message d'erreur.
   * @param s la chaéne de caractéres dans laquelle on lit
   * @return l'entier lu (0 en cas d'erreur) */
  public static int toInt(String s) {
    int result = 0;

    result = Integer.valueOf(s).intValue();
    return result;
  }

  /**
   * Cette méthode lit un réel (double) dans une chaéne de caractéres.
   * Elle s'occupe des éventuelles exceptions. Si une exception
   * (NumberFormatException) est attrapée, la méthode renvoie le réel
   * nul et affiche un message d'erreur.
   * @param s la chaéne de caractéres dans laquelle on lit
   * @return le réel lu (0 en cas d'erreur)
   */
  public static double toDouble(String s) {
    double result = 0;

    result = Double.valueOf(s).doubleValue();
    return result;
  }

  /**
   * Cette méthode lit une ligne sur l'entrée standard et s'occupe des
   * éventuelles exceptions. Si une exception (IOException) est
   * attrapée, la méthode renvoie une chaéne vide et affiche un
   * message d'erreur.
   * @return la chaéne lue (vide en cas d'erreur)
   */
  public static String lireString() throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String result;
    result = br.readLine();

    return result;
  }

  /**
   * Cette méthode lit un entier sur l'entrée standard.
   * Elle s'occupe des éventuelles exceptions et utilise la méthode
   * toInt pour obtenir un entier. S'il y a un probléme de lecture,
   * la méthode affiche un message d'erreur et renvoie 0.
   * @return l'entier lu (0 en cas d'erreur)
   */
  public static int lireInt() throws IOException {
    return toInt(lireString());
  }

  /**
   * Cette méthode lit un réel sur l'entrée standard.
   * Elle s'occupe des éventuelles exceptions et utilise la méthode
   * toDouble pour obtenir un réel. S'il y a un probléme de lecture,
   * la méthode affiche un message d'erreur et renvoie 0.
   * @return le réel lu (0 en cas d'erreur)
   */
  public static double lireDouble() throws IOException {
    return toDouble(lireString());
  }

  /**
   * Cette méthode lit un booléen sur l'entrée standard.
   * Elle s'occupe des éventuelles exceptions et renvoie false
   * s'il y a un probléme de lecture.
   * @return le booléen lu (false en cas d'erreur)
   */
  public static boolean lireBoolean() throws IOException {
    return Boolean.valueOf(lireString()).booleanValue();
  }

  /**
   * Cette méthode lit un caractére sur l'entrée standard.
   * Elle s'occupe des éventuelles exceptions et renvoie NULL
   * s'il y a un probléme de lecture.
   * N.B.: si plusieurs caractéres sont entrés, c'est le premier
   * qui est pris en compte.
   * @return le (premier) caractére lu (caractére spécial NULL en cas d'erreur)
   */
  public static char lireChar() throws IOException {
    char result = '\u0000';
    try {
      result = lireString().charAt(0);
    }
    catch (IndexOutOfBoundsException e) {
      System.err.println("Erreur de lecture: aucun caractére entré");
    }
    return result;
  }
}
