
package com;

/**
 * @author Andrey Grabarnick
 * @email reist2009@gmail.com
 * @date 23 May 2018
 * <p>
 * Contract Constant Strings Manager holds: configuration locations +
 * communication language.
 */
public class Contract {
    final public static int DEFAULT_PORT = 5555;// Default fall-back port
    final public static String clientFXML = "/client/gui/fxml/"; // location of FXML files for the client
    final public static String serverFXML = "/server/gui/fxml/"; // location of FXML files for the server
    final public static String css = "/com/style/style.css"; // location of CSS file
    final public static String graphics = "/com/graphics/"; //location of graphics dir

    // Client-Server Communication Language
    // Authorization Vocabulary
    final public static int AUTHORIZE = 2; //Authorize User in Message
    final public static int AUTH_YES = 1; // AUTHORIZE positive message
    final public static int AUTH_NO = 0; // AUTHORIZE negative message
    final public static int LOG_OFF = -1; // Logoff user


    // Exam Vocabulary
    final public static int EXAM = 10;
    final public static int CREATE_EXAM = 11;
    final public static int READ_EXAM = 12;
    final public static int UPDATE_EXAM = 13;
    final public static int DELETE_EXAM = 14;
    final public static int GET_EXAMS_BY_SUBJECT = 15;
    final public static int GET_EXAMS_BY_COURSE = 16;


    // Question vocabulary
    final public static int QUESTION = 20;
    final public static int CREATE_QUESTION = 21;
    final public static int READ_QUESTION = 22;
    final public static int UPDATE_QUESTION = 23;
    final public static int DELETE_QUESTION = 24;
    final public static int GET_QUESTIONS_BY_SUBJECT = 25;
    final public static int GET_QUESTIONS_BY_EXAM = 26;
    final public static int GET_QUESTIONS_BY_TEACHER = 27;

    // Courses/Subject Vocabulary
    final public static int COURSE = 30;
    final public static int SUBJECT = 31;
    final public static int GET_COURSE = 32;
    final public static int GET_SUBJECT = 33;

    //Grades Vocabulary
    final public static int GET_GRADES_BY_EXAM = 41;
    final public static int GET_GRADES_BY_STUDENT = 42;


}
